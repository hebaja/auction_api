package com.hebaja.auction.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.gson.Gson;
import com.hebaja.auction.model.Auctioneer;
import com.hebaja.auction.service.AuctioneerService;

@RestController
@CrossOrigin
@RequestMapping("/api/test")
public class TestApiRest {
	
	@Autowired
	private AuctioneerService service;
	
	@GetMapping
	public ResponseEntity<String> test() {
		System.out.println("running test");
		return ResponseEntity.ok("it is running");
	}
	
	@GetMapping("creating-a-user")
	public String create() {
		Auctioneer auctioneer = new Auctioneer("auctioneer", "hebajabackup@gmail.com");
		try {
			service.save(auctioneer);
			return "user saved";
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	
	@GetMapping("message/{id}")
	public ResponseEntity<?> sendFirebaseMessage(@PathVariable("id") Long id) {
		Auctioneer auctioneer = service.findById(id);
		
		Gson gson = new Gson();
		String json = gson.toJson(auctioneer);
		
		
		Message message = Message.builder()
			.putData("user_with_subject", json)
			.setTopic(auctioneer.getEmail())
			.build();
		
		try {
			String response = FirebaseMessaging.getInstance().send(message);
			System.out.println("Message successfully sent: " + response + " with topic: " + auctioneer.getEmail());
			return ResponseEntity.ok("message sent");
		} catch (FirebaseMessagingException e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.badRequest().build();
		
	}
	
	@PostMapping("message/token")
	public ResponseEntity<?> sendFirebaseMessageToken(@RequestBody String token) throws FirebaseMessagingException {
		
		System.out.println(token);
		
		String registrationToken = token;

		// See documentation on defining a message payload.
		Message message = Message.builder()
		    .putData("score", "850")
		    .putData("time", "2:45")
		    .setToken(registrationToken)
		    .build();

		// Send a message to the device corresponding to the provided
		// registration token.
		String response = FirebaseMessaging.getInstance().send(message);
		// Response is a message ID string.
		System.out.println("Successfully sent message: " + response);
		
		return ResponseEntity.ok("received");
		
//		Auctioneer auctioneer = service.findById(id);
//		
//		Gson gson = new Gson();
//		String json = gson.toJson(auctioneer);
//		
//		
//		Message message = Message.builder()
//			.putData("user_with_subject", json)
//			.setTopic(auctioneer.getEmail())
//			.build();
//		
//		try {
//			String response = FirebaseMessaging.getInstance().send(message);
//			System.out.println("Message successfully sent: " + response + " with topic: " + auctioneer.getEmail());
//			return ResponseEntity.ok("message sent");
//		} catch (FirebaseMessagingException e) {
//			e.printStackTrace();
//		}
//		
//		return ResponseEntity.badRequest().build();
		
	}

}
