package com.karatesan.ImageUploader.exception.controller;

import com.karatesan.ImageUploader.exception.ImageNotFoundException;
import com.karatesan.ImageUploader.exception.InternalServerException;
import com.karatesan.ImageUploader.exception.MalformedFileException;
import com.karatesan.ImageUploader.exception.UnsupportedDataTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;

import java.awt.*;
import java.io.IOException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ImageNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleImageNotFoundException(ImageNotFoundException exception){
        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND.value(),exception.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorResponse> handleIOException(IOException exception){
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage()+" cos sie zjebalo");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(UnsupportedDataTypeException.class)
    public ResponseEntity<ErrorResponse> handleUnsupportedDataTypeException(UnsupportedDataTypeException ex){
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
    @ExceptionHandler(MalformedFileException.class)
    public ResponseEntity<ErrorResponse> handleMalformedFileException(MalformedFileException ex){
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<ErrorResponse> handleInternalServerError(InternalServerException ex){
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Oops! Something went wrong!");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
