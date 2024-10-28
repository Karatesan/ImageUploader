package com.karatesan.ImageUploader.utility;

import com.karatesan.ImageUploader.exception.MalformedFileException;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class ImageUploadUtility {

    private final static List<String> supportedContentTypes = List.of("image/jpeg","image/jpg","image/webp","image/png");

    public static long getNextGroupNumber(String uploadDirectory) throws IOException {
        try (Stream<Path> paths = Files.list(Paths.get(uploadDirectory))) {
            return paths
                    .filter(Files::isDirectory)
                    .map(p->p.getFileName().toString())
                    .map(name -> {
                        try {
                            return Long.parseLong(name);
                        } catch (NumberFormatException e) {
                            return 0L; //
                        }
                    })
                    .max(Long::compare)
                    .orElse(0L) +1;
        }
    }

    public static String getLastFromSplitString(String toSplit,String regex){
        if(toSplit == null || toSplit.isEmpty()){
            throw new IllegalArgumentException("Input string cannot be null");
        }
        String[] s = toSplit.split(regex);
        return s[s.length-1];
    }

    public static String getOriginalImageName(String uniqueImageName){
        return getLastFromSplitString(uniqueImageName,"_");
    }

    public static Long getGroupId(String uploadPath){
        Path path = Path.of(uploadPath);
        return Long.parseLong(path.getFileName().toString());
        //return Long.parseLong(getLastFromSplitString(uploadPath,"/"));
    }

    public static String getImageExtension(String filename){
        return getLastFromSplitString(getOriginalImageName(filename),"\\.");
    }

    public static MediaType getContentType(String filename){
        return MediaTypeFactory.getMediaType(filename).get();
    }

    public static boolean isImageValid(MultipartFile image){
        return supportedContentTypes.contains(image.getContentType()) &&
               // arePropertiesValid(image) &&
                isMagicNumbersValid(image);
    }

    public static boolean arePropertiesValid(MultipartFile file){
        try (InputStream inputStream = file.getInputStream()) {
            BufferedImage image = ImageIO.read(inputStream);
            return image != null && image.getWidth() > 0 && image.getHeight() > 0;
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean isMagicNumbersValid(MultipartFile file){

        try(InputStream inputStream = file.getInputStream()){
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
