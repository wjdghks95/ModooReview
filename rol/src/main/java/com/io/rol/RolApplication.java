package com.io.rol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class RolApplication {

	public static void main(String[] args) {
		SpringApplication.run(RolApplication.class, args);
	}

}
