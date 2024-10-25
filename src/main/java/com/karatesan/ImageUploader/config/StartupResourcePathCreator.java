//package com.karatesan.ImageUploader.config;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//
//@Component
//public class StartupResourcePathCreator implements ApplicationRunner {
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//
//            Path path = Path.of(this.getUploadDirectory());
//            if(!Files.exists(path))
//                Files.createDirectories(path);
//
//    }
//}
