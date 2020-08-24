package com.appsdeveloperblog.photoapp.api.PhotoAppAlbums;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class PhotoAppAlbumsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhotoAppAlbumsApplication.class, args);
	}

}
