package com.karatesan.ImageUploader.service;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public  class ImageValidator {

    private final static List<String> supportedContentTypes = List.of("image/jpeg", "image/jpg", "image/webp", "image/png");


    public static boolean isImageValid(MultipartFile image) {
        return supportedContentTypes.contains(image.getContentType()) &&
                arePropertiesValid(image) &&
                isMagicNumbersValid(image);
    }

    public static boolean arePropertiesValid(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            BufferedImage image = ImageIO.read(inputStream);
            return image != null && image.getWidth() > 0 && image.getHeight() > 0;
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean isMagicNumbersValid(MultipartFile file) {

        try (InputStream inputStream = file.getInputStream()) {
            byte[] header = new byte[8];
            inputStream.read(header);
            String fileSignature = bytesToHex(header);
            return fileSignature.startsWith("FFD8FF") || // JPEG
                    fileSignature.startsWith("89504E47") || // PNG
                    fileSignature.startsWith("52494646");   // WebP

        } catch (IOException e) {
            return false;
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            hexString.append(String.format("%02X", b));
        }
        return hexString.toString();
    }
}
