package com.hebaja.auction.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.hebaja.auction.repository.AuctioneerRepository;

@Configuration
@EnableWebSecurity
//@Profile("dev")
public class WebSecurityConfigDev extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private AppUserDetailsService userDetails;
	
	@Autowired
	private AuctioneerRepository auctioneerRepository;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			.antMatchers(
					HttpMethod.GET,
					"/api/player",
					"/api/reset-password",
					"/api/test/**",
					"/api/test",
					"/api/auction/list")
				.permitAll()
			.antMatchers(
					HttpMethod.POST,
					"/api/player/**",
					"/api/firebase-auth")
				.permitAll()
			.antMatchers(
					HttpMethod.GET,
//					"/api/auction",
					"/api/auction/**",
					"/api/auctioneer/**")
				.authenticated()
			.antMatchers(
					HttpMethod.POST, 
					"/api/auction/**",
					"/api/group-player/**",
					"/api/auctioneer/delete")
				.authenticated()
			.antMatchers(HttpMethod.PUT,
					"/api/auction",
					"/api/group-player")
				.authenticated()
			.anyRequest().permitAll()
			.and().csrf().disable().headers().frameOptions().disable()
			.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and().addFilterBefore(new AuthenticatioViaTokenFilter(auctioneerRepository), UsernamePasswordAuthenticationFilter.class);
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetails);
	}
	

}
