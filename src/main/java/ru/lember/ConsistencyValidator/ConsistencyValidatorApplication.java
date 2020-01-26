package ru.lember.ConsistencyValidator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class ConsistencyValidatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsistencyValidatorApplication.class, args);

		log.info("Spring Boot application started");
	}

}
