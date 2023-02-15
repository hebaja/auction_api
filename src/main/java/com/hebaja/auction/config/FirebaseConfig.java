package com.hebaja.auction.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Configuration
@Service
public class FirebaseConfig {
	
	private static final String TAG = FirebaseConfig.class.toString();

	public void configure(Environment environment) throws IOException {
		
		InputStream firebaseCredentialStream = null;
		if(!firebaseAppHasBeenInitialized()) {
			if(Arrays.asList(environment.getActiveProfiles()).contains("dev")) {
				FileInputStream serviceAccount = new FileInputStream("/home/focus/my_developer_key/firebase/auction-5271a-firebase-adminsdk-4r35f-acd271f679.json");
				FirebaseOptions options = FirebaseOptions
						.builder()
						.setCredentials(GoogleCredentials.fromStream(serviceAccount))
						.build();
				FirebaseApp.initializeApp(options);
				System.out.println(TAG + " Starting firebase in " + environment.getActiveProfiles()[0] + " mode with app name " + FirebaseApp.DEFAULT_APP_NAME );
			} else if(Arrays.asList(environment.getActiveProfiles()).contains("prod")) {
				try {
					firebaseCredentialStream = createFirebaseCredential();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				System.out.println(firebaseCredentialStream);
				
				FirebaseOptions options = FirebaseOptions
						.builder()
						.setCredentials(GoogleCredentials.fromStream(firebaseCredentialStream))
						.build();
				FirebaseApp.initializeApp(options);
				System.out.println(TAG + " Starting firebase in " + environment.getActiveProfiles()[0] + " mode with app name " + FirebaseApp.DEFAULT_APP_NAME );
			}
		} else {
			System.out.println(TAG + " Firebase has already been started in " + environment.getActiveProfiles()[0] + " mode with app name " + FirebaseApp.DEFAULT_APP_NAME);
		}
	}
	
	private boolean firebaseAppHasBeenInitialized() {
		boolean hasBeenInitialized = false;
		List<FirebaseApp> firebaseApps = FirebaseApp.getApps();
		for(FirebaseApp app : firebaseApps){
		    if(app.getName().equals(FirebaseApp.DEFAULT_APP_NAME)){
		        hasBeenInitialized = true;
		    }
		}
		return hasBeenInitialized;
	}
	
	private InputStream createFirebaseCredential() throws Exception {
	    String privateKey = System.getenv("FIREBASE_PRIVATE_KEY").replace("\\n", "\n");

	    FirebaseCredential firebaseCredential = new FirebaseCredential();
	    firebaseCredential.setType(System.getenv("FIREBASE_TYPE"));
	    firebaseCredential.setProject_id(System.getenv("FIREBASE_PROJECT_ID"));
	    firebaseCredential.setPrivate_key_id(System.getenv("FIREBASE_PRIVATE_KEY_ID"));
	    firebaseCredential.setPrivate_key(privateKey);
	    firebaseCredential.setClient_email(System.getenv("FIREBASE_CLIENT_EMAIL"));
	    firebaseCredential.setClient_id(System.getenv("FIREBASE_CLIENT_ID"));
	    firebaseCredential.setAuth_uri(System.getenv("FIREBASE_AUTH_URI"));
	    firebaseCredential.setToken_uri(System.getenv("FIREBASE_TOKEN_URI"));
	    firebaseCredential.setAuth_provider_x509_cert_url(System.getenv("FIREBASE_AUTH_PROVIDER_X509_CERT_URL"));
	    firebaseCredential.setClient_x509_cert_url(System.getenv("FIREBASE_CLIENT_X509_CERT_URL"));
	    //serialization of the object to json string
	    ObjectMapper mapper = new ObjectMapper();
	    String jsonString = mapper.writeValueAsString(firebaseCredential);

	    //convert jsonString string to InputStream using Apache Commons
	    
	    InputStream inputStream = IOUtils.toInputStream(jsonString, StandardCharsets.UTF_8);
	    
	    return inputStream; 
	}

}
