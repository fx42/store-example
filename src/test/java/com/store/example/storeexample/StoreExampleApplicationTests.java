package com.store.example.storeexample;

import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@EmbeddedKafka(
        topics = {"${topics.event-topic}", "${topics.config-topic}"},
        brokerProperties = "log.dir=target/${random.uuid}/embedded-kafka",
        bootstrapServersProperty = "spring.kakfa.bootstrap-servers"
)
@DirtiesContext
@TestPropertySource(properties = {
        "spring.kafka.bootstrap-servers=${spring.kakfa.bootstrap-servers}",

})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StoreExampleApplicationTests {

	@Autowired
    EmbeddedKafkaBroker embeddedKafkaBroker;

	DefaultKafkaProducerFactory<byte[], String> eventProducerFactory;
    DefaultKafkaProducerFactory<String, String> configProducerFactory;


    @BeforeAll
    void setup() {
        final Map<String, Object> producerProps = KafkaTestUtils.producerProps(embeddedKafkaBroker);
        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        configProducerFactory = new DefaultKafkaProducerFactory<>(producerProps);

		producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.ByteArraySerializer");
		eventProducerFactory = new DefaultKafkaProducerFactory<>(producerProps);
	}

	@Test
    void test() {
        // ARRANGE
        final KafkaTemplate<String, String> incomingConfigProducer = new KafkaTemplate<>(configProducerFactory, true);
        incomingConfigProducer.setDefaultTopic("config-input-topic");
        final KafkaTemplate<byte[], String> incommingEventProducer = new KafkaTemplate<>(eventProducerFactory, true);
        incommingEventProducer.setDefaultTopic("event-input-topic");

        // ACT
        incomingConfigProducer.sendDefault("configId", "my-new-config");
        incommingEventProducer.sendDefault(new byte[1], "event-which-contains-the-config-id");

    }

}