package com.personal.coronavirustracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CoronavirustrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoronavirustrackerApplication.class, args);
	}

}
