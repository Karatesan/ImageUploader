package com.karatesan.ImageUploader.utility;

import com.karatesan.ImageUploader.service.interfaces.FileServiceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.file.Path;

@Service
public class FilePathCreator {

    @Autowired
    FileServiceConfig fileServiceConfig;

    public String getImageUrl(String fileName, String groupId) {
        return UriComponentsBuilder.fromUriString(fileServiceConfig.getRootUri())
                .path("/download/{rootFolder}/{groupId}/{fileName}")
                .buildAndExpand(fileServiceConfig.getUploadDirectory(),groupId,fileName)
                .toUriString();

        //return String.join("/",fileServiceConfig.getRootUri(),"download",fileServiceConfig.getUploadDirectory(),groupId,fileName);
    }

    public Path getImagePath(String fileName, String groupId){
        Path resolve = Path.of(fileServiceConfig.getUploadDirectory()).resolve(groupId).resolve(fileName);
        System.out.println(fileName);
        return resolve;
    }
}
