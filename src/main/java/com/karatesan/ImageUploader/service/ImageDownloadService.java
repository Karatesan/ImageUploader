package com.karatesan.ImageUploader.service;

import com.karatesan.ImageUploader.dto.request.ImageRequestDtoGetImage;
import com.karatesan.ImageUploader.dto.response.AllImagesFromGroupResponseDto;
import com.karatesan.ImageUploader.dto.response.ImageResponseDtoLocation;
import com.karatesan.ImageUploader.exception.ImageNotFoundException;
import com.karatesan.ImageUploader.exception.ImageReadException;
import com.karatesan.ImageUploader.exception.UnsupportedDataTypeException;
import com.karatesan.ImageUploader.service.interfaces.FileServiceConfig;
import com.karatesan.ImageUploader.utility.FilePathCreator;
import com.karatesan.ImageUploader.utility.ImageUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.List;

@Service
public class ImageDownloadService {

    private final FileServiceConfig fileServiceConfig;
    private final FilePathCreator filePathCreator;
    private final ImageResourceStreamCreator imageResourceStreamCreator;


    public ImageDownloadService(FileServiceConfig fileServiceConfig, FilePathCreator filePathCreator, ImageResourceStreamCreator imageResourceStreamCreator) {
        this.fileServiceConfig = fileServiceConfig;
        this.filePathCreator = filePathCreator;
        this.imageResourceStreamCreator = imageResourceStreamCreator;
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
                        .buildAndExpand(imageRequest.groupId(), imageRequest.fileName())
                        .toUriString();
            }
            default -> throw new UnsupportedDataTypeException("Unsupported data type: " + imageRequest.dataType());
        }
    }

    public InputStreamResource getImageResourceStream(String groupId, String fileName){
        Path imagePath = filePathCreator.getImagePath(fileName, groupId);

        File image = imagePath.toFile();
        if (!image.exists()) {
            throw new ImageNotFoundException(fileName);
        }
            return imageResourceStreamCreator.getImageResourceStream(image);
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

    //TODO handle throw more gracefully
    public AllImagesFromGroupResponseDto getAllImagesFromGroup(String groupId) throws IOException {
        Path groupPath = Path.of(fileServiceConfig.getUploadDirectory()).resolve(groupId);
        List<ImageResponseDtoLocation> images = Files.list(groupPath)
                .map(file -> {
                    String filename = ImageUtility.getOriginalImageName(file.getFileName().toString());
                    String mimeType = ImageUtility.getContentType(filename).toString();
                    return new ImageResponseDtoLocation(filename, groupId, filePathCreator.getImageUrl(filename, groupId).toString(), mimeType);
                })
                .toList();
        return new AllImagesFromGroupResponseDto(images);
    }
}
