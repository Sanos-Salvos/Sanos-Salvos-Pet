package com.sanossalvos.pet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PetApplication {
	public static void main(String[] args) {
		SpringApplication.run(PetApplication.class, args);
	}
}