package com.hebaja.auction.api;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

@RestController
@CrossOrigin
@RequestMapping("/api/reset-password")
public class ResetPasswordRest {

	@GetMapping("{email}")
	public String resetPassword(@PathVariable("email") String email) {
		System.out.println(email);
		String link;
		try {
			link = FirebaseAuth.getInstance().generatePasswordResetLink(email);
			System.out.println("generated link -> " + link);
			return link;
		} catch (FirebaseAuthException e) {
			System.out.println("message -> " + e.getMessage());
			return e.getMessage();
		}
	}
	
	@GetMapping("verify/{email}")
	public String verifyEmail(@PathVariable("email") String email) {
		System.out.println(email);
		String link;
		try {
			link = FirebaseAuth.getInstance().generateEmailVerificationLink(email);
			System.out.println("generated link -> " + link);
			return link;
		} catch (FirebaseAuthException e) {
			System.out.println("message -> " + e.getMessage());
			return e.getMessage();
		}
		
	}

	
//	@GetMapping
//	public String reset() {
//		System.out.println("email");
//		
//		return "accepted";
//		
//	}
	
}
