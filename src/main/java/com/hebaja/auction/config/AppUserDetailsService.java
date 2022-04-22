package com.hebaja.auction.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hebaja.auction.model.Auctioneer;
import com.hebaja.auction.repository.AuctioneerRepository;

@Service
public class AppUserDetailsService implements UserDetailsService {

	@Autowired
	private AuctioneerRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<Auctioneer> userOptional = repository.findByEmail(email);
		if(userOptional.isPresent()) {
			return userOptional.get();
		} else {
			throw new UsernameNotFoundException("User not found");
		}
	
	}

}
