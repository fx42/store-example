package com.store.example.storeexample.processors;

import org.apache.kafka.streams.processor.api.Processor;
import org.apache.kafka.streams.processor.api.ProcessorContext;
import org.apache.kafka.streams.processor.api.Record;
import org.apache.kafka.streams.state.KeyValueStore;

public class StateStoreProcessor implements Processor<String, String, Void, Void>{

    private final String statestoreName;
    private KeyValueStore<String,String> globalStore;

    public StateStoreProcessor(String statestoreName){
        this.statestoreName = statestoreName;
    }

    @Override
    public void init(ProcessorContext<Void,Void> processorContext) {
        this.globalStore = processorContext.getStateStore(statestoreName);
    }

    @Override
    public void process(Record<String, String> record) {
        globalStore.put(record.key(), record.value());
    }
    
}
