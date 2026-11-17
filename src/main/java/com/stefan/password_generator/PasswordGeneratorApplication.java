package com.stefan.password_generator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** Entry point that starts the Spring Boot password generator application. */
@SpringBootApplication
public class PasswordGeneratorApplication {

	public static void main(String[] args) {
		// Bootstrap Spring and its application context.
		SpringApplication.run(PasswordGeneratorApplication.class, args);
	}

}
