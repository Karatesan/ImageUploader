package com.karatesan.ImageUploader.controller;

import com.karatesan.ImageUploader.dto.response.ImageResponse;
import com.karatesan.ImageUploader.service.ImageDownloadService;
import com.karatesan.ImageUploader.service.ImageUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("upload")
@CrossOrigin(origins = "http://localhost:5173")
public class ImageUploadController {

    @Autowired
    private ImageUploadService imageUploadService;
    @Autowired
    private ImageDownloadService imageDownloadService;
//   public ImageResponse uploadImage(@RequestParam List<MultipartFile> images){
    //TODOMa zwrocic link do resourca
    @PostMapping
    public ImageResponse uploadImage(@RequestParam("image") MultipartFile image){
        try {
            return imageUploadService.saveImage(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //TODO exception dla zlego typu danych w requescie
}
