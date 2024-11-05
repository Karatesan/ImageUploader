package com.karatesan.ImageUploader.service;

import com.karatesan.ImageUploader.exception.ImageNotFoundException;
import com.karatesan.ImageUploader.utility.FilePathCreator;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;


@Service
public class ImageResourceStreamCreator {

    public InputStreamResource getImageResourceStream(File file) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            return new InputStreamResource(fileInputStream);

        } catch (IOException ex) {
            throw new ImageNotFoundException("Image not found");
        }
    }

    //FileSystemResource is a wrapper around file, not stream
    public FileSystemResource getImageAsFileSystemResource(File file) {

        return new FileSystemResource(file);
    }

}
