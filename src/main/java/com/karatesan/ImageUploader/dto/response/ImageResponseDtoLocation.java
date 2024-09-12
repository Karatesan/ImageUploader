package com.karatesan.ImageUploader.dto.response;

public record ImageResponseDtoLocation(String fileName, String location, String mimeType) implements ImageResponse {
}
