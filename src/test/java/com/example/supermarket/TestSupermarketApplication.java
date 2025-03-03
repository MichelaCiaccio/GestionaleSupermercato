package com.example.supermarket;

import org.springframework.boot.SpringApplication;

public class TestSupermarketApplication {

	public static void main(String[] args) {
		SpringApplication.from(SupermarketApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
