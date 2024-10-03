package com.karatesan.ImageUploader.service;

import com.karatesan.ImageUploader.service.interfaces.FileServiceConfig;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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
//TODO lepiej ogarnac errora ioexcepion z try-catch
    @PostConstruct
    public void initializeUploadPath() throws IOException {
        Path path = Path.of(this.getUploadDirectory());
            if(!Files.exists(path))
                Files.createDirectories(path);
    }
}
