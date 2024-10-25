package com.karatesan.ImageUploader.service;

import com.karatesan.ImageUploader.dto.request.ImageRequestDtoGetImage;
import com.karatesan.ImageUploader.exception.ImageNotFoundException;
import com.karatesan.ImageUploader.exception.ImageReadException;
import com.karatesan.ImageUploader.exception.UnsupportedDataTypeException;
import com.karatesan.ImageUploader.service.interfaces.FileServiceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

@Service
public class ImageDownloadService {


    private final FileServiceConfig fileServiceConfig;

    @Autowired
    public ImageDownloadService(FileServiceConfig fileServiceConfig) {
        this.fileServiceConfig = fileServiceConfig;
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
            case RESOURCE -> {
                return UriComponentsBuilder.fromUriString(fileServiceConfig.getImageResourceUri())
                        .path("/{groupId}/{fileName}")
                        .buildAndExpand(imageRequest.groupId(),imageRequest.fileName())
                        .toUriString();
            }
            default -> throw new UnsupportedDataTypeException("Unsupported data type: " + imageRequest.dataType());
        }
    }

    public InputStreamResource getImageResourceStream(Long groupId, String fileName) throws FileNotFoundException {
        Path imagePath = Path.of(fileServiceConfig.getUploadDirectory()).resolve(groupId.toString()).resolve(fileName);
        File image = imagePath.toFile();
        if(!image.exists()){
            throw new ImageNotFoundException(fileName);
        }
        FileInputStream fileInputStream = new FileInputStream(image);
        return new InputStreamResource(fileInputStream);
    }

    public byte[] getImageAsBytes(String imageName, long groupId) {
        Path imagePath = Path.of(fileServiceConfig.getUploadDirectory()).resolve(String.valueOf(groupId)).resolve(imageName);
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
}
