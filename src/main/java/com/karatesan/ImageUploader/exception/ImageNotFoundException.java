package com.karatesan.ImageUploader.exception;

public class ImageNotFoundException extends RuntimeException{
    public ImageNotFoundException(String fileName){
        super("Image with name: "+fileName+" not found.");
    }
}
