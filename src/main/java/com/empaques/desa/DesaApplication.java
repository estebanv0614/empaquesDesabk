package com.empaques.desa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DesaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesaApplication.class, args);
	}

}
