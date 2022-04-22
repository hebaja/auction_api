package com.hebaja.auction;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

import com.hebaja.auction.config.FirebaseConfig;
import com.hebaja.auction.task.ThreadPoolService;

@SpringBootApplication
@EnableCaching
public class AuctionApplication implements EnvironmentAware{

	public static void main(String[] args) {
		SpringApplication.run(AuctionApplication.class, args);
		new ThreadPoolService().create();
	}

	@Override
	public void setEnvironment(Environment environment) {
		if (environment.getActiveProfiles().length == 0) {
            throw new RuntimeException("A valid Spring profile must be supplied!");
        }
		try {
			new FirebaseConfig().configure(environment);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
