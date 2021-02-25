package com.example.convertcurrency;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ConvertcurrencyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConvertcurrencyApplication.class, args);
	}

}
