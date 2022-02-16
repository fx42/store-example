package com.store.example.storeexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafkaStreams;

@EnableKafkaStreams
@SpringBootApplication
public class StoreExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(StoreExampleApplication.class, args);
	}

}
