package com.karatesan.ImageUploader.service;

import com.karatesan.ImageUploader.dto.ImageResponseDto;
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

        ImageResponseDto image = imageUploadService.saveImage(multipartFile);

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

        ImageResponseDto image = imageUploadService.saveImage(multipartFile);
        System.out.println(ImageUploadUtility.getGroupId(image.location()));
        byte[] expectedImage = Files.readAllBytes(Path.of(image.location()).resolve(image.fileName()));
        byte[] actualImage = imageUploadService.getImageAsBytes(image.fileName(),ImageUploadUtility.getGroupId(image.location()));


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