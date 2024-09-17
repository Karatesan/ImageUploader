package com.karatesan.ImageUploader.service;

import com.karatesan.ImageUploader.dto.request.ImageRequestDtoGetImage;
import com.karatesan.ImageUploader.dto.response.ImageResponseDtoLocation;
import com.karatesan.ImageUploader.exception.ImageNotFoundException;
import com.karatesan.ImageUploader.exception.ImageReadException;
import com.karatesan.ImageUploader.exception.UnsupportedDataTypeException;
import com.karatesan.ImageUploader.utility.ImageUploadUtility;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.UUID;

@Setter
@Service
public class ImageUploadService {

    @Value("${upload.dir}")
    private String uploadDirectory;

    public ImageResponseDtoLocation saveImage(MultipartFile image, long groupId) throws IOException {

        if (image == null) {
            throw new IllegalArgumentException("Image cannot be null");
        }

        String uniqueFileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
        Path uploadPath = Path.of(uploadDirectory).resolve(String.valueOf(groupId));
        Path filePath = uploadPath.resolve(uniqueFileName);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return new ImageResponseDtoLocation(uniqueFileName, uploadPath.toString(), image.getContentType());
    }

    public ImageResponseDtoLocation saveImage(MultipartFile image) throws IOException {
        return saveImage(image, ImageUploadUtility.getNextGroupNumber(uploadDirectory));
    }

    //======================================================================================

    public byte[] getImageAsBytes(String imageName, long groupId) {
        Path imagePath = Path.of(uploadDirectory).resolve(String.valueOf(groupId)).resolve(imageName);
        File image = imagePath.toFile();
        if (!image.exists())
            throw new ImageNotFoundException(imageName);
        try {
            return Files.readAllBytes(imagePath);
        } catch (IOException e) {
            throw new ImageReadException("Error reading image: " + imageName, e);
        }
    }

    public String getImageAsBase64String(String imageName, long groupId) {
        byte[] imageAsBytes = this.getImageAsBytes(imageName, groupId);
        return Base64.getEncoder().encodeToString(imageAsBytes);
    }

//TODO as stream
    public Object getImage(ImageRequestDtoGetImage imageRequest) {
        String fileName = imageRequest.fileName();
        long group = imageRequest.groupId();
        switch (imageRequest.dataType()) {
            case BYTES -> {
                return getImageAsBytes(fileName, group);
            }
            case BASE64 -> {
                return getImageAsBase64String(fileName, group);
            }
            default -> throw new UnsupportedDataTypeException("Unsupported data type: " + imageRequest.dataType());
        }
    }
}

//public ImageResponse getImage(ImageRequestDtoGetImage imageRequest) {
//    String fileName = imageRequest.fileName();
//    long group = imageRequest.groupId();
//    switch (imageRequest.dataType()) {
//        case BYTES -> {
//            byte[] imageAsBytes = getImageAsBytes(fileName, group);
//            return new ImageResponseDtoBytes(ImageUploadUtility.getImageExtension(imageRequest.fileName()), imageAsBytes);
//        }
//        case BASE64 -> {
//            String imageAsBase64String = getImageAsBase64String(fileName, group);
//            return new ImageResponseDtoBase64(ImageUploadUtility.getImageExtension(imageRequest.fileName()), imageAsBase64String);
//        }
//        default -> throw new UnsupportedDataTypeException("Unsupported data type: " + imageRequest.dataType());
//    }
//}
