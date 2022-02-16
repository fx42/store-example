package com.store.example.storeexample;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;

@SpringBootTest
@EmbeddedKafka
class StoreExampleApplicationTests {

	@Test
	void contextLoads() {
		Assertions.assertTrue(true);
	}

}