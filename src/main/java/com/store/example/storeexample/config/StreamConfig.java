package com.store.example.storeexample.config;

import com.store.example.storeexample.processors.StateStoreProcessor;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Time;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.state.Stores;
import org.apache.kafka.streams.state.internals.KeyValueStoreBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.StreamsBuilderFactoryBeanConfigurer;

@Configuration
public class StreamConfig {

    @Value("${topics.event-topic}")
    private String eventInputTopic;

    @Value("${topics.config-topic}")
    private String configInputTopic;

    @Value("${statestore.name}")
    private String statestoreName;

    public void configureGlobalStateStore(StreamsBuilder streamsBuilder) {
        final var keyValueStore = new KeyValueStoreBuilder<>(
                Stores.inMemoryKeyValueStore("config-input-topic"),
                Serdes.String(),
                Serdes.String(),
                Time.SYSTEM
        );

        streamsBuilder.addGlobalStore(
                keyValueStore.withLoggingDisabled(),
                "config-input-topic",
                Consumed.with(Serdes.String(), Serdes.String()),
                () -> new StateStoreProcessor(statestoreName));
    }

    @Bean
    public StreamsBuilderFactoryBeanConfigurer streamsBuilderFactoryBeanCustomizer() {
        return factoryBean -> {
                try {
                    final StreamsBuilder streamsBuilder = factoryBean.getObject();
                    configureGlobalStateStore(streamsBuilder);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        };
    }
}
