package com.example.parcial1a;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.example")
@EnableReactiveMongoRepositories(basePackages = "com.example.productos.repository")
public class Parcial1AApplication {
	public static void main(String[] args) {
		SpringApplication.run(Parcial1AApplication.class, args);
	}
}


