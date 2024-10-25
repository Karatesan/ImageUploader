package com.karatesan.ImageUploader;

import com.karatesan.ImageUploader.service.FileServiceConfigImpl;
import com.karatesan.ImageUploader.service.ImageDownloadService;
import com.karatesan.ImageUploader.service.ImageUploadService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ImageUploaderApplication {

	public static void main(String[] args) {

		SpringApplication.run(ImageUploaderApplication.class, args);

//		//FileServiceConfigImpl config = new FileServiceConfigImpl();
//		ImageDownloadService serviceD = new ImageDownloadService(config);
//		ImageUploadService serviceU = new ImageUploadService(config);
	}
}
