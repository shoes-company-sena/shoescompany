package com.shoescompany;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class ShoesCompanyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoesCompanyApplication.class, args);
	}

}
