package com.karatesan.ImageUploader.exception;

import java.io.IOException;

public class ImageReadException extends RuntimeException {
    public ImageReadException(String s, Throwable p1) {
        super(s,p1);
    }
}
