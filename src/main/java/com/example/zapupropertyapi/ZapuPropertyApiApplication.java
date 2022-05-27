package com.example.zapupropertyapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ZapuPropertyApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(ZapuPropertyApiApplication.class, args);
	}

}
