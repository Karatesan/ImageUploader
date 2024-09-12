package com.karatesan.ImageUploader.dto;

public record ImageBytesDto(String mimeType, byte[] image) {
}
