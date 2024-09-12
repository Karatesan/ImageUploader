package com.karatesan.ImageUploader.dto.request;

import org.springframework.http.MediaType;

public enum FileResponseDataType {
    BYTES(MediaType.IMAGE_JPEG),
    BASE64(MediaType.TEXT_PLAIN);

    private final MediaType mediaType;

    FileResponseDataType(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    public MediaType getContentType(){
        return mediaType;
    }
}
