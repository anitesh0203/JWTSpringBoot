package com.example.jwtImplementation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"delegate","models","security","services","utils","filters"})
public class JwtImplementationApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtImplementationApplication.class, args);
	}

}
