package com.productboard.productboarddemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ProductboardDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductboardDemoApplication.class, args);
	}

}
