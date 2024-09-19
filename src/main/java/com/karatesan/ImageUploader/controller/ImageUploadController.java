package com.karatesan.ImageUploader.controller;

import com.karatesan.ImageUploader.dto.response.ImageResponse;
import com.karatesan.ImageUploader.dto.response.ImageResponseDtoLocation;
import com.karatesan.ImageUploader.service.ImageDownloadService;
import com.karatesan.ImageUploader.service.ImageUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ImageResponse> uploadImage(@RequestParam("image") MultipartFile image){
        try {
            ImageResponseDtoLocation imageResponseDtoLocation = imageUploadService.saveImage(image);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(imageResponseDtoLocation);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //TODO exception dla zlego typu danych w requescie
}
