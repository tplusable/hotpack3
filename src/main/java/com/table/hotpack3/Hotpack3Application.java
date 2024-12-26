package com.table.hotpack3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Hotpack3Application {

	public static void main(String[] args) {
		SpringApplication.run(Hotpack3Application.class, args);
	}

}
