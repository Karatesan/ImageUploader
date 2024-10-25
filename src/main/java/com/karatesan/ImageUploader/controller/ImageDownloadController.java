package com.karatesan.ImageUploader.controller;

import com.karatesan.ImageUploader.dto.request.ImageRequestDtoGetImage;
import com.karatesan.ImageUploader.service.ImageDownloadService;
import com.karatesan.ImageUploader.utility.ImageUploadUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.FileNotFoundException;

@RestController
@RequestMapping("download")
@CrossOrigin(origins = "http://localhost:5173")
public class ImageDownloadController {

    @Autowired
    private ImageDownloadService imageDownloadService;

    public ImageDownloadController(ImageDownloadService imageDownloadService) {
        this.imageDownloadService = imageDownloadService;
    }

    //TODO exception dla zlego typu danych w requescie

    @PostMapping("/getImage")
    public ResponseEntity<?> getImage(@RequestBody ImageRequestDtoGetImage imageRequest){
//jak mam klase, ktora zawiera byte[] spring automatycznei serializuje to do base 64
        Object image = imageDownloadService.getImage(imageRequest);
        MediaType contentType = ImageUploadUtility.getContentType(imageRequest.fileName());
        return ResponseEntity
                .ok()
                .contentType(contentType!=null ? contentType : MediaType.IMAGE_JPEG)
                .body(image);
    }

    @GetMapping("/images/{groupId}/{fileName}")
    public ResponseEntity<InputStreamResource> getImageResource(@PathVariable("groupId") Long groupId,
                                                                @PathVariable("fileName")String fileName) throws FileNotFoundException {
        MediaType contentType = ImageUploadUtility.getContentType(fileName);
        InputStreamResource imageResourceStream = imageDownloadService.getImageResourceStream(groupId, fileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"inline; filename=\"" + ImageUploadUtility.getOriginalImageName(fileName) + "\"")
                .contentType(contentType!=null ? contentType : MediaType.IMAGE_JPEG)
                .body(imageResourceStream);
    }
}
