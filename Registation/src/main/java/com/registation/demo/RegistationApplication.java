package com.registation.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RegistationApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegistationApplication.class, args);
	}

	// Hash all existing user passwords with BCrypt on startup
	@org.springframework.context.annotation.Bean
	public org.springframework.boot.CommandLineRunner ensureAdminUser(
			com.registation.demo.service.UserService userService) {
		return args -> {
			userService.createAdminIfNotExists();
		};
	}

}
