package com.developerb.jvents;

import com.developerb.jvents.aggregate.ReflectiveAggregateRoot;

import java.util.List;

/**
 *
 */
public abstract class TestAggregate extends ReflectiveAggregateRoot {


    public TestAggregate(String aggregateId, List<?> events) {
        super(aggregateId, events);
    }

}
