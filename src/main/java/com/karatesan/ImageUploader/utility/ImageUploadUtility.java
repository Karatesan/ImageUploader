package com.karatesan.ImageUploader.utility;

import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class ImageUploadUtility {

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

}
