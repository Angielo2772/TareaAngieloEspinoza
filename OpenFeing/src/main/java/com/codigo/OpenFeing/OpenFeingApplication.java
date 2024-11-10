package com.codigo.OpenFeing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class OpenFeingApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpenFeingApplication.class, args);
	}

}
