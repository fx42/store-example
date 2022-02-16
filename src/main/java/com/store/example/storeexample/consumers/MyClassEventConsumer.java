package com.store.example.storeexample.consumers;

import java.util.function.Consumer;

import com.store.example.storeexample.processors.MyClassEventProcessor;

import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("process-event")
public class MyClassEventConsumer implements Consumer<KStream<byte[], String>> {

    private final String statestoreName;

    public MyClassEventConsumer(@Value("$statestore.name") String statestoreName) {
        this.statestoreName = statestoreName;    
    }

    @Override
    public void accept(KStream<byte[], String> t) {
        t.process(() -> new MyClassEventProcessor(statestoreName), statestoreName);
    }
}
