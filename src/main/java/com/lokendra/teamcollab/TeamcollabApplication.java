package com.lokendra.teamcollab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class TeamcollabApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeamcollabApplication.class, args);
	}

}
