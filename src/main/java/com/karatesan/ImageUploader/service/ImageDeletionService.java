package com.karatesan.ImageUploader.service;


import com.karatesan.ImageUploader.service.interfaces.FileServiceConfig;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

@Service
public class ImageDeletionService {

    private FileServiceConfig fileServiceConfig;

    public ImageDeletionService(FileServiceConfig fileServiceConfig) {
        this.fileServiceConfig = fileServiceConfig;
    }

    public void deleteImage(Long groupId, String imageName){


    }
}
