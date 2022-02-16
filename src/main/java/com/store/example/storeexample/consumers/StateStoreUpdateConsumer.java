package com.store.example.storeexample.consumers;

import java.util.function.Consumer;

import com.store.example.storeexample.processors.StateStoreProcessor;

import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("process-config")
public class StateStoreUpdateConsumer implements Consumer<KStream<String, String>> {

    private final String statestoreName;

    public StateStoreUpdateConsumer(@Value("$statestore.name") String statestoreName) {
        this.statestoreName = statestoreName;
        
    }
    @Override
    public void accept(KStream<String, String> t) {
        t.process(() -> new StateStoreProcessor(statestoreName), statestoreName);
    }
}
