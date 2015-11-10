package com.developerb.jvents;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 *
 */
public abstract class EventStoreTest {

    private EventStore<TestEvent, TestAggregate> store;

    @Before
    public final void initializeEventStore() throws Exception {
        store = createEventStoreUnderTest();
    }

    protected abstract EventStore<TestEvent, TestAggregate> createEventStoreUnderTest();


    @Test
    public void appendFirstEventThenReadBack() throws Exception {
        TestEvent first = new SomeEvent("Nasse Nøff", 28);

        store.append("aggregate-x", 0, Collections.singletonList(first));


        SomeAggregate aggregate = store.readAggregate(SomeAggregate.class, "aggregate-x");


        assertNotNull(aggregate);
        assertEquals("aggregate-x", aggregate.getId());
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
