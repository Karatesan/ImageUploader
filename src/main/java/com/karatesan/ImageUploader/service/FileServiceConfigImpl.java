package com.karatesan.ImageUploader.service;

import com.karatesan.ImageUploader.service.interfaces.FileServiceConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
public class FileServiceConfigImpl implements FileServiceConfig {

    @Value("${upload.dir}")
    private String uploadDirectory;
    @Value("${app.api.image-resource-uri}")
    private String imageResourceUri;

    @Override
    public String getUploadDirectory() {
        return uploadDirectory;
    }

    @Override
    public String getImageResourceUri() {
        return imageResourceUri;
    }
}
