package com.store.example.storeexample.processors;

import org.apache.kafka.streams.processor.api.Processor;
import org.apache.kafka.streams.processor.api.ProcessorContext;
import org.apache.kafka.streams.processor.api.Record;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.beans.factory.annotation.Value;

public class MyClassEventProcessor implements Processor<byte[], String, Void, Void> {

    private final String statestoreName;
    private KeyValueStore<String, String> globalStore;

    public MyClassEventProcessor(@Value("$statestore.name") String statestoreName){
        this.statestoreName = statestoreName;
    }

    @Override
    public void init(ProcessorContext<Void,Void> processorContext) {
        this.globalStore = processorContext.getStateStore(statestoreName);
    }

    @Override
    public void process(Record<byte[], String> record) {
        globalStore.get(record.value());
        
    }
    
}
