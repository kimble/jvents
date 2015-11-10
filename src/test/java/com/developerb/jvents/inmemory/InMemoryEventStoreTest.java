package com.developerb.jvents.inmemory;

import com.developerb.jvents.*;
import com.developerb.jvents.aggregate.ReflectiveAggregateFactory;
import com.developerb.jvents.serializer.GsonEventSerializer;

import java.util.List;


public class InMemoryEventStoreTest extends EventStoreTest {

    @Override
    protected EventStore<TestEvent, TestAggregate> createEventStoreUnderTest() {
        EventSerializer<TestEvent> serializer = new GsonEventSerializer<>();
        AggregateFactory<TestAggregate, TestEvent> aggregateFactory = new ReflectiveAggregateFactory<TestAggregate, TestEvent>() {

            @Override
            public <G extends TestAggregate> List<TestEvent> getDirtyEvents(G aggregate) {
                return aggregate.dirtyEvents();
            }

            @Override
            public <G extends TestAggregate> String getId(G aggregate) {
                return aggregate.id();
            }

            @Override
            public <G extends TestAggregate> long currentRevision(G aggregate) {
                return aggregate.currentRevision();
            }

        };

        return new InMemoryEventStore<>(serializer, aggregateFactory);
    }

}