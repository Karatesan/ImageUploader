package com.karatesan.ImageUploader.controller;

import com.karatesan.ImageUploader.dto.request.ImageRequestDtoGetImage;
import com.karatesan.ImageUploader.dto.response.ImageResponse;
import com.karatesan.ImageUploader.dto.response.ImageResponseDtoBytes;
import com.karatesan.ImageUploader.dto.response.ImageResponseDtoLocation;
import com.karatesan.ImageUploader.service.ImageUploadService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("upload")
@CrossOrigin(origins = "http://localhost:5174")
public class ImageUploadController {

    @Autowired
    private ImageUploadService imageUploadService;



    @PostMapping
    public ImageResponse uploadImage(@RequestParam List<MultipartFile> images){
        try {
            ImageResponseDtoLocation image = imageUploadService.saveImage(images.get(0));
            return  image;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //TODO exception dla zlego typu danych w requescie

    @PostMapping("/getImage")
    public ResponseEntity<?> getImage(@RequestBody ImageRequestDtoGetImage imageRequest){
//jak mam klase, ktora zawiera byte[] spring automatycznei serializuje to do base 64
        Object image = imageUploadService.getImage(imageRequest);
        return ResponseEntity
                .ok()
                .contentType(imageRequest.dataType().getContentType())
                .body(image);
    }
}
