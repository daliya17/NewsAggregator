package com.assignment.newsaggregator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class NewsAggregatorServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewsAggregatorServiceApplication.class, args);
	}

}
