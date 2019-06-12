package com.cloud.config.server.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
//spring cloud server 注解
@EnableConfigServer
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
