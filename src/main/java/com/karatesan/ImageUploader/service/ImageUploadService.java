package com.karatesan.ImageUploader.service;

import com.karatesan.ImageUploader.dto.response.ImageResponseDtoLocation;
import com.karatesan.ImageUploader.dto.response.UnsavedImageResponseDto;
import com.karatesan.ImageUploader.dto.response.UploadedImagesResponseDto;
import com.karatesan.ImageUploader.service.interfaces.FileServiceConfig;
import com.karatesan.ImageUploader.utility.FilePathCreator;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Setter
@Service
public class ImageUploadService {


    private final FileServiceConfig fileServiceConfig;
    private final FilePathCreator filePathCreator;

    public ImageUploadService(FileServiceConfig fileServiceConfig, FilePathCreator filePathCreator) {
        this.fileServiceConfig = fileServiceConfig;
        this.filePathCreator = filePathCreator;
    }

    public ImageResponseDtoLocation saveImage(MultipartFile image, String groupId) throws IOException {

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

        return new ImageResponseDtoLocation(uniqueFileName,
                groupId,
                filePathCreator.getImageUrl(uniqueFileName, groupId),
                image.getContentType());
    }

    public UploadedImagesResponseDto saveImages(MultipartFile[] images, String groupId) {

        List<UnsavedImageResponseDto> unsavedImages = new ArrayList<>();
        List<ImageResponseDtoLocation> savedImages = new ArrayList<>();

        for (MultipartFile image : images) {
            if (!ImageValidator.isImageValid(image)) {
                unsavedImages.add(new UnsavedImageResponseDto(image.getOriginalFilename(), "Unsupported or malformed file."));
                continue;
            }
            try {
                ImageResponseDtoLocation imageResponseDtoLocation = this.saveImage(image, groupId);
                savedImages.add(imageResponseDtoLocation);
            } catch (IOException e) {
                UnsavedImageResponseDto unsavedImage = new UnsavedImageResponseDto(image.getOriginalFilename(), e.getMessage());
                unsavedImages.add(unsavedImage);
            }
        }

        return new UploadedImagesResponseDto(groupId, savedImages, unsavedImages);
    }
}

