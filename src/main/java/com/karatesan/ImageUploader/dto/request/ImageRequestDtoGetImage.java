package com.karatesan.ImageUploader.dto.request;

import lombok.NonNull;
import lombok.Value;

public record ImageRequestDtoGetImage(
        @NonNull String fileName,
        @NonNull  Long groupId,
        @NonNull FileResponseDataType dataType) {
}

