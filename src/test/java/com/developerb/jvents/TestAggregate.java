package com.developerb.jvents;

import com.developerb.jvents.aggregate.ReflectiveAggregateRoot;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 *
 */
public abstract class TestAggregate extends ReflectiveAggregateRoot<TestEvent> {

    /**
     * Used for restoring already existing aggregates
     *
     * @param aggregateId
     * @param events
     */
    public TestAggregate(String aggregateId, List<TestEvent> events) {
        super(aggregateId, events);
    }

    /**
     * For new aggregates with a random id
     */
    public TestAggregate() {
        super(UUID.randomUUID().toString(), Collections.emptyList());
    }

    /**
     * For new aggregates with a given id
     */
    public TestAggregate(String id) {
        super(id, Collections.emptyList());
    }

}
