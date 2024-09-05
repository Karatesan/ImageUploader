package com.karatesan.ImageUploader.service;

import com.karatesan.ImageUploader.dto.ImageResponseDto;
import com.karatesan.ImageUploader.exception.ImageNotFoundException;
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
import java.util.UUID;

@Setter
@Service
public class ImageUploadService {

    @Value("${upload.dir}")
    private String uploadDirectory;

    public ImageResponseDto saveImage(MultipartFile image, long groupId) throws IOException {

        if(image == null){
            throw new IllegalArgumentException("Image cannot be null");
        }

        String uniqueFileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
        Path uploadPath = Path.of(uploadDirectory).resolve(String.valueOf(groupId));
        Path filePath = uploadPath.resolve(uniqueFileName);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return new ImageResponseDto(uniqueFileName,uploadPath.toString(), image.getContentType());
    }

    public ImageResponseDto saveImage(MultipartFile image) throws IOException {
        return saveImage(image, ImageUploadUtility.getNextGroupNumber(uploadDirectory));
    }

    //======================================================================================

    public byte[] getImageAsBytes(String imageName, long groupId){
        Path imagePath = Path.of(uploadDirectory).resolve(String.valueOf(groupId)).resolve(imageName);
        File image = imagePath.toFile();

        if(!image.exists())
            throw new ImageNotFoundException(imageName);


        return null;
    }


}
