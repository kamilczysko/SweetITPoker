package com.walczak.itpoker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ItPokerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItPokerApplication.class, args);
	}

}
