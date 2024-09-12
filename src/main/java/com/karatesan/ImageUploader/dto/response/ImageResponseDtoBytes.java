package com.karatesan.ImageUploader.dto.response;

public record ImageResponseDtoBytes(String mimeType, byte[] imageData) implements ImageResponse{
}
