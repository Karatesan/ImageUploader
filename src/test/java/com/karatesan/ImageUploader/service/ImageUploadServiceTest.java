package com.karatesan.ImageUploader.service;

import com.karatesan.ImageUploader.dto.ImageResponseDto;
import com.karatesan.ImageUploader.dto.response.ImageResponseDtoLocation;
import com.karatesan.ImageUploader.exception.ImageNotFoundException;
import com.karatesan.ImageUploader.utility.ImageUploadUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.io.TempDir;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ImageUploadServiceTest {

    MultipartFile multipartFile;
    ImageUploadService imageUploadService;
    @TempDir
    Path tempDir;
    private final String testImagePath = "src/test/resources/test-image.jpg";


    @BeforeEach
    void init() throws IOException {
        multipartFile = createMultiPartFile(new File(testImagePath));
        imageUploadService = new ImageUploadService();
        imageUploadService.setUploadDirectory(tempDir.toString());
    }

    //====================================================================================================

    @Test
    public void testGetNextGroupNumberReturns1WhenImagesDirectoryIsEmpty() throws IOException {

        long nextGroupNumber = ImageUploadUtility.getNextGroupNumber(tempDir.toString());

        assertEquals(1L, nextGroupNumber);
    }

    @Test
    public void testGetNextGroupNumberReturns2WhenThereIsOneFolderInImages() throws IOException {

        Files.createDirectory(tempDir.resolve("1"));

        long nextGroupNumber = ImageUploadUtility.getNextGroupNumber(tempDir.toString());

        assertEquals(2L, nextGroupNumber);
    }

    @Test
    public void testGetNextGroupNumberSkipsNonNumbericDirectoriesNames() throws IOException {

        Files.createDirectory(tempDir.resolve("1"));
        Files.createDirectory(tempDir.resolve("2"));
        Files.createDirectory(tempDir.resolve("asdasd"));

        long nextGroupNumber = ImageUploadUtility.getNextGroupNumber(tempDir.toString());

        assertEquals(3L, nextGroupNumber);
    }

    //====================================================================================================

    @Test
    public void testGetOriginalImageNameReturnsImageNameWithProperStringProvided() throws IOException {
        String uploadedImageName = "1312-3123-4235-5345_original-file-name.jpg";
        String originalFileName = "original-file-name.jpg";

        assertEquals(originalFileName, ImageUploadUtility.getOriginalImageName(uploadedImageName));
    }

    @Test
    public void testGetOriginalImageNameThrowsIllegalArgumentExceptionWhenProvidedWithEmptyString() {
        String uploadedImageName = "";
        assertThrows(IllegalArgumentException.class, () -> ImageUploadUtility.getOriginalImageName(uploadedImageName));
    }

    @Test
    public void testGetOriginalImageNameThrowsIllegalArgumentExceptionWhenProvidedWithNull() {
        String uploadedImageName = null;
        assertThrows(IllegalArgumentException.class, () -> ImageUploadUtility.getOriginalImageName(uploadedImageName));
    }

    @Test
    public void testGetGroupIdReturnsProperIdWhenProvidedWithProperStringPath() {
        String uploadPath = "/images/1/";

        assertEquals(1L, ImageUploadUtility.getGroupId(uploadPath));
    }

    //====================================================================================================

    @Test
    public void testSaveImage() throws IOException {

        ImageResponseDtoLocation image = imageUploadService.saveImage(multipartFile);

        File savedFile = new File(tempDir + "/1/" + image.fileName());
        assertTrue(savedFile.exists());
        assertEquals(multipartFile.getName(), ImageUploadUtility.getOriginalImageName(image.fileName()));
        assertEquals(image.mimeType(), multipartFile.getContentType());
    }

    @Test
    public void testSaveImageThrowsIllegalArgumentExceptionWhenMultipartFileIsNull() throws IOException {
        multipartFile = null;
        assertThrows(IllegalArgumentException.class, () -> imageUploadService.saveImage(multipartFile));
    }

    //====================================================================================================

    @Test
    public void testGetImageAsBytesThrowsImageNotFoundExceptionWhenProvidedWithWrongFilename() {

        assertThrows(ImageNotFoundException.class, () -> imageUploadService.getImageAsBytes("asdsdasd", 2));
    }

    @Test
    public void testGetImageAsBytesRetrievesImageWhenExists() throws IOException {
//        Path groupDir = tempDir.resolve("1");
//        Files.createDirectories(groupDir);
//
//        String imageName = "testImage.png";
//        Path imagePath = groupDir.resolve(imageName);
//
//        byte[] expectedContent = "image-data".getBytes();
//        Files.write(imagePath, expectedContent);
//
//        byte[] actualContent = imageUploadService.getImageAsBytes(imageName, 1L);
        String imageName = "testImage.jpg";
        Path uploadPath = tempDir.resolve("1");
        Files.createDirectories(uploadPath);
        byte[] expectedImageBytes = Files.readAllBytes(Path.of(testImagePath));
        Files.write(uploadPath.resolve(imageName),expectedImageBytes);

        byte[] actualContent = imageUploadService.getImageAsBytes(imageName, 1L);


        assertArrayEquals(expectedImageBytes, actualContent, "The image content should match.");
    }

    @Test
    public void testGetImageAsBytesThrowsImageNotFoundExceptionWhenThereIsNoImageInProvidedPath(){

        String fakeImageName = "no-image-here.jpg";
        long fakeGroupId = 20L;

        assertThrows(ImageNotFoundException.class, ()-> imageUploadService.getImageAsBytes(fakeImageName,fakeGroupId));
    }

    @Test
    public void testGetImageAsBase64StringRetrievesConvertedImage() throws IOException {
        String imageName = "testImage.jpg";
        Path uploadPath = tempDir.resolve("1");
        Files.createDirectories(uploadPath);
        byte[] expectedImageBytes = Files.readAllBytes(Path.of(testImagePath));
        String base64EncodedFile = Base64.getEncoder().encodeToString(expectedImageBytes);
        Files.write(uploadPath.resolve(imageName),expectedImageBytes);

        String imageAsBase64String = imageUploadService.getImageAsBase64String(imageName, 1L);

        Path p = Path.of("src/test/resources/base.txt");
        Path p2 = Path.of("src/test/resources/bytes.txt");
        Files.write(p,imageAsBase64String.getBytes());
        Files.write(p2,expectedImageBytes);


        assertEquals(base64EncodedFile,imageAsBase64String);

    }

    //====================================================================================================

    private MultipartFile createMultiPartFile(File file) throws IOException {
        try (FileInputStream input = new FileInputStream(file)) {
            return new MockMultipartFile(
                    file.getName(),
                    file.getName(),
                    "image/jpg",
                    input
            );
        }
    }

}