package com.karatesan.ImageUploader.controller;

import com.karatesan.ImageUploader.dto.response.UploadedImagesResponseDto;
import com.karatesan.ImageUploader.service.ImageDownloadService;
import com.karatesan.ImageUploader.service.ImageUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<UploadedImagesResponseDto> uploadImages(@RequestParam("images") MultipartFile[] images, @RequestParam String groupId) {
//testujemy to dla kazdego indywidualnego zdjecia w servisie
//        Arrays.stream(images).forEach(image -> {
//            if (!ImageUploadUtility.isImageValid(image))
//                throw new MalformedFileException("Unsupported or malformed file.");
//        });

        UploadedImagesResponseDto uploadedImagesResponseDto = imageUploadService.saveImages(images, groupId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(uploadedImagesResponseDto);
    }

    //TODO exception dla zlego typu danych w requescie
}
