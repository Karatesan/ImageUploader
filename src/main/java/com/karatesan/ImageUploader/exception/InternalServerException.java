package com.karatesan.ImageUploader.exception;

public class InternalServerException extends RuntimeException{

    public InternalServerException() {
        super("Oops! Something went wrong!");
    }
}
