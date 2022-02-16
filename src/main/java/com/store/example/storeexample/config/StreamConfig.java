package com.store.example.storeexample.config;

import com.store.example.storeexample.processors.StateStoreProcessor;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.StoreBuilder;
import org.apache.kafka.streams.state.Stores;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.StreamsBuilderFactoryBeanCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StreamConfig {

    @Value("${topics.event-topic}")
    private String eventInputTopic;

    @Value("${topics.config-topic}")
    private String configInputTopic;

    @Value("${statestore.name}")
    private String statestoreName;

    // @Bean
    // StreamsBuilderFactoryBeanCustomizer streamsBuilderFactoryBeanCustomizer(
    //         StoreBuilder<KeyValueStore<String, String>> storeBuilder) {

    //     return factoryBean -> {
    //         try {
    //             var streamBuilder = factoryBean.getObject();
    //             streamBuilder.addGlobalStore(storeBuilder, configInputTopic,
    //                     Consumed.with(Serdes.String(), Serdes.String()), () -> new StateStoreProcessor(statestoreName));

    //         } catch (Exception e) {
    //             e.printStackTrace();
    //         }
    //     };
    // }

    @Bean
    public StoreBuilder<KeyValueStore<String, String>> globalConfigStore() {
        return Stores.keyValueStoreBuilder(
                Stores.persistentKeyValueStore(statestoreName),
                Serdes.String(),
                Serdes.String());
    }
}
