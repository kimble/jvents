package com.developerb.jvents.aggregate;

import com.developerb.jvents.AggregateFactory;
import com.developerb.jvents.TestAggregate;
import com.developerb.jvents.TestEvent;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 *
 */
public class ReflectiveAggregateFactoryTest {

    @Test
    public void canCreateAggregateInstance() throws Exception {
        AggregateFactory<TestAggregate, TestEvent> factory = new ReflectiveAggregateFactory<>();

        SomeAggregate aggregate = factory.createInstance(SomeAggregate.class, "some-stream-id", Collections.singletonList (
                new SomeEvent("Ole Brumm", 25)
        ));

        assertNotNull(aggregate);
        assertEquals("some-stream-id", aggregate.getId());
    }



    public static class SomeEvent extends TestEvent {

        String name;
        int age;

        public SomeEvent(String name, int age) {
            this.name = name;
            this.age = age;
        }

    }


    public static class SomeAggregate extends TestAggregate {

        public SomeAggregate(String id, List<TestEvent> events) {
            super(id, events);
        }

        protected void on(SomeEvent event) {

        }

    }

}