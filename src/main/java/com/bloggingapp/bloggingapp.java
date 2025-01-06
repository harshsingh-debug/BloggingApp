package com.bloggingapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bloggingapp.utils.ObjectMapping;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@ComponentScan(basePackages = "com.bloggingapp")
@EnableJpaRepositories(basePackages = "com.bloggingapp.repositories")
public class bloggingapp implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Bean
	public ObjectMapping objectMapping(ModelMapper modelMapper) {
		return new ObjectMapping(modelMapper);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(bloggingapp.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Password dude: " + passwordEncoder.encode("Password@1"));
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		boolean isPasswordMatch = encoder.matches("Password@1", "$2a$10$tgvgEd1hVa9c0MQDSzSK1O21yyI8AfP9nq5HS7AqPg5AzrbEeGn3u");
		System.out.println("Password matches: " + isPasswordMatch);
	}
}
