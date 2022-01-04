//package com.hebaja.auction.config;
//
//import java.net.URI;
//import java.net.URISyntaxException;
//
//import javax.sql.DataSource;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class DatabaseConfig {
//	
//	@Bean
//	public DataSource postgresDataSource() {
//		String databaseUrl = System.getenv("DATABASE_URL");
//		URI dbUri;
//		try {
//			dbUri = new URI(databaseUrl);
//		} catch (URISyntaxException e) {
//			e.printStackTrace();
//			return null;
//		}
//		String username = dbUri.getUserInfo().split(":")[0];
//		String password = dbUri.getUserInfo().split(":")[1];
//		String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ":" + dbUri.getPort() + dbUri.getPath();
//		
//		org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();
//	
//		dataSource.setUrl(dbUrl);
//		dataSource.setUsername(username);
//		dataSource.setPassword(password);
//		return dataSource;
//	}
//
//}
