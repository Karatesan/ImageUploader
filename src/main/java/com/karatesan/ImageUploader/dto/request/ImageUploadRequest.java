package com.karatesan.ImageUploader.dto.request;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record ImageUploadRequest(List<MultipartFile>images) {
}
