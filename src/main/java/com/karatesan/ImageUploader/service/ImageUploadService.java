package com.karatesan.ImageUploader.service;

import com.karatesan.ImageUploader.dto.response.ImageResponseDtoLocation;
import com.karatesan.ImageUploader.service.interfaces.FileServiceConfig;
import com.karatesan.ImageUploader.utility.ImageUploadUtility;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import java.util.UUID;

@Setter
@Service
public class ImageUploadService {

    @Autowired
    private FileServiceConfig fileServiceConfig;

    public ImageResponseDtoLocation saveImage(MultipartFile image, long groupId) throws IOException {

        if (image == null) {
            throw new IllegalArgumentException("Image cannot be null");
        }

        String uniqueFileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
        Path uploadPath = Path.of(fileServiceConfig.getUploadDirectory()).resolve(String.valueOf(groupId));
        Path filePath = uploadPath.resolve(uniqueFileName);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return new ImageResponseDtoLocation(uniqueFileName,  Long.valueOf(groupId).toString(), image.getContentType());
    }

    public ImageResponseDtoLocation saveImage(MultipartFile image) throws IOException {
        return saveImage(image, ImageUploadUtility.getNextGroupNumber(fileServiceConfig.getUploadDirectory()));
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
