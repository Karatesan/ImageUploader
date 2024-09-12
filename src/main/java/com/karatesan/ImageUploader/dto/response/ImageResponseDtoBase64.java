package com.karatesan.ImageUploader.dto.response;

public record ImageResponseDtoBase64(String mimeType, String imageData) implements ImageResponse {
}
