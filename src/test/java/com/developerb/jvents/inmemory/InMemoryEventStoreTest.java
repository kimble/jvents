package com.developerb.jvents.inmemory;

import com.developerb.jvents.*;
import com.developerb.jvents.aggregate.ReflectiveAggregateFactory;
import com.developerb.jvents.serializer.GsonEventSerializer;

/**
 *
 */
public class InMemoryEventStoreTest extends EventStoreTest {

    @Override
    protected EventStore<TestEvent, TestAggregate> createEventStoreUnderTest() {
        EventSerializer<TestEvent> serializer = new GsonEventSerializer<>();
        AggregateFactory<TestAggregate, TestEvent> aggregateFactory = new ReflectiveAggregateFactory<>();

        return new InMemoryEventStore<>(serializer, aggregateFactory);
    }

}