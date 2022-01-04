package com.hebaja.auction.config;

import java.io.FileInputStream;
import java.io.IOException;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

public class FirebaseConfig {
	
	public void configure() throws IOException {
		FileInputStream credentials = new FileInputStream("/app/auction-5271a-firebase-adminsdk-4r35f-eddbeffed8.json");
		GoogleCredentials googleCredentials = GoogleCredentials.fromStream(credentials);
		FirebaseOptions options = FirebaseOptions
				.builder()
				.setCredentials(googleCredentials)
				.build();
		
		System.out.println(this.getClass().toString() + " starting firebase");
		
		FirebaseApp.initializeApp(options);
		System.out.println("[FirebaseConfig] Starting firebase with app name: " + FirebaseApp.DEFAULT_APP_NAME );
	}

}
