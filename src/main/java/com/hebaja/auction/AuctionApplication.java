package com.hebaja.auction;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.ResponseEntity;

import com.google.firebase.FirebaseApp;
import com.hebaja.auction.config.FirebaseConfig;
import com.hebaja.auction.task.ThreadPoolService;

@SpringBootApplication
@EnableCaching
public class AuctionApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuctionApplication.class, args);
		FirebaseConfig firebase = new FirebaseConfig();
		new ThreadPoolService().create();
		try {
			firebase.configure();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
