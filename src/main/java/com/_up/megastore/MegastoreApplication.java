package com._up.megastore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class MegastoreApplication {
	public static void main(String[] args) {
		SpringApplication.run(MegastoreApplication.class, args);
	}
}