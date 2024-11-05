package com.karatesan.ImageUploader.controller;

import com.karatesan.ImageUploader.dto.request.ImageRequestDtoGetImage;
import com.karatesan.ImageUploader.dto.response.AllImagesFromGroupResponseDto;
import com.karatesan.ImageUploader.service.ImageDownloadService;
import com.karatesan.ImageUploader.utility.ImageUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("download")
@CrossOrigin(origins = "http://localhost:5173")
public class ImageDownloadController {


    private final ImageDownloadService imageDownloadService;

    public ImageDownloadController(ImageDownloadService imageDownloadService) {
        this.imageDownloadService = imageDownloadService;
    }

    //TODO exception dla zlego typu danych w requescie

    @PostMapping("/getImage")
    public ResponseEntity<?> getImage(@RequestBody ImageRequestDtoGetImage imageRequest){
//jak mam klase, ktora zawiera byte[] spring automatycznei serializuje to do base 64
        Object image = imageDownloadService.getImage(imageRequest);
        MediaType contentType = ImageUtility.getContentType(imageRequest.fileName());
        return ResponseEntity
                .ok()
                .contentType(contentType!=null ? contentType : MediaType.IMAGE_JPEG)
                .body(image);
    }

    @GetMapping("/images/{groupId}/{fileName}")
    public ResponseEntity<InputStreamResource> getImageResource(@PathVariable("groupId") String groupId,
                                                                @PathVariable("fileName")String fileName)  {
        InputStreamResource imageResourceStream = imageDownloadService.getImageResourceStream(groupId, fileName);
        MediaType contentType = ImageUtility.getContentType(fileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"inline; filename=\"" + ImageUtility.getOriginalImageName(fileName) + "\"")
                .contentType(contentType!=null ? contentType : MediaType.IMAGE_JPEG)
                .body(imageResourceStream);
    }

    @GetMapping("/images/{groupId}")
    public ResponseEntity<AllImagesFromGroupResponseDto> getAllImagesUrls(@PathVariable("groupId") String groupId) throws IOException {

        return ResponseEntity.ok(imageDownloadService.getAllImagesFromGroup(groupId));

    }
}
