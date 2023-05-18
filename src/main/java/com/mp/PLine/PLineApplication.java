package com.mp.PLine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PLineApplication {
	public static void main(String[] args) {
		SpringApplication.run(PLineApplication.class, args);
	}

}
