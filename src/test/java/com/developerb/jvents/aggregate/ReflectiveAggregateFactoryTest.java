package com.developerb.jvents.aggregate;

import com.developerb.jvents.AggregateFactory;
import com.developerb.jvents.TestAggregate;
import com.developerb.jvents.TestEvent;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 *
 */
public class ReflectiveAggregateFactoryTest {

    @Rule
    public ExpectedException ex = ExpectedException.none();


    @Test
    public void canCreateAggregateInstance() throws Exception {
        AggregateFactory<ReflectiveAggregateRoot, TestEvent> factory = new ReflectiveAggregateFactory<>();

        SomeAggregate aggregate = factory.createInstance(SomeAggregate.class, "some-stream-id", Collections.singletonList (
                new SomeEvent("Ole Brumm", 25)
        ));

        assertNotNull(aggregate);
        assertEquals("some-stream-id", aggregate.getId());
    }

    @Test
    public void detectAggregatesWithoutAppropraiteConstructor() throws Exception {
        ex.expect(AggregateFactory.AggregateCreationFailure.class);
        ex.expectMessage("Failed to create instance of class com.developerb.jvents.aggregate.ReflectiveAggregateFactoryTest$AggregateWithoutAppropriateConstructor " +
                "with id <some-stream-id>: Missing appropriate constructor (String aggregateId, List<E> events)");

        AggregateFactory<ReflectiveAggregateRoot, TestEvent> factory = new ReflectiveAggregateFactory<>();

        factory.createInstance(AggregateWithoutAppropriateConstructor.class, "some-stream-id", Collections.singletonList (
                new SomeEvent("Ole Brumm", 25)
        ));
    }

    

    public static class SomeEvent extends TestEvent {

        String name;
        int age;

        public SomeEvent(String name, int age) {
            this.name = name;
            this.age = age;
        }

    }


    public static class SomeAggregate extends ReflectiveAggregateRoot {

        public SomeAggregate(String id, List<TestEvent> events) {
            super(id, events);
        }

        protected void on(SomeEvent event) {

        }

    }

    public static class AggregateWithoutAppropriateConstructor extends ReflectiveAggregateRoot {

        public AggregateWithoutAppropriateConstructor() {
            super(null, null);
        }

    }

}