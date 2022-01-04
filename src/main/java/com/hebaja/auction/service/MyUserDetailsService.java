//package com.hebaja.auction.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import com.hebaja.auction.config.MyUserPrincipal;
//import com.hebaja.auction.model.Auctioneer;
//
//@Service
//public class MyUserDetailsService implements UserDetailsService{
//	
//	@Autowired
//	private AuctioneerService service;
//
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		Auctioneer auctioneer = service.findByUsername(username);
//		if(auctioneer == null) {
//			throw new UsernameNotFoundException("user not found");
//		}
//		return new MyUserPrincipal(auctioneer);
//	}
//}
