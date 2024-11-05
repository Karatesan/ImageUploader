package com.karatesan.ImageUploader.dto.response;

import java.util.List;

public record UploadedImagesResponseDto(String groupId, List<ImageResponseDtoLocation> uploadedImages, List<UnsavedImageResponseDto> unsavedImages ) {
}
