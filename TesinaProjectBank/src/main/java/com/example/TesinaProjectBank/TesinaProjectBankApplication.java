package com.example.TesinaProjectBank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.TesinaProjectBank.repository.UserRepository;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses= UserRepository.class)
public class TesinaProjectBankApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(TesinaProjectBankApplication.class, args);
	}
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/proyects").allowedOrigins("http://localhost:8500");
			}
		};
	}
}
