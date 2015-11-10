package com.developerb.jvents.aggregate;

import com.developerb.jvents.TestEvent;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class ReflectiveAggregateRootTest {


    @Test
    public void createInstanceFromOldEvents() throws Exception {
        CalculatorAggregate aggregate = new CalculatorAggregate("xyz", Arrays.asList (
                new NumberAddedEvent(1),
                new NumberAddedEvent(2),
                new NumberAddedEvent(3)
        ));

        assertEquals(6, aggregate.sum);
        assertFalse(aggregate.isDirty());
    }

    @Test
    public void addAdditionalNumber() throws Exception {
        CalculatorAggregate aggregate = new CalculatorAggregate("xyz", Arrays.asList (
                new NumberAddedEvent(1),
                new NumberAddedEvent(2),
                new NumberAddedEvent(3)
        ));


        aggregate.addNumber(5);
        assertTrue(aggregate.isDirty());


        assertEquals (
                Collections.singletonList (
                        new NumberAddedEvent(5)
                ),

                aggregate.dirtyEvents()
        );
    }



    public static class NumberAddedEvent extends TestEvent {

        int number;

        public NumberAddedEvent(int number) {
            this.number = number;
        }

    }


    public static class CalculatorAggregate extends ReflectiveAggregateRoot<TestEvent> {

        public int sum;

        public CalculatorAggregate(String id, List<TestEvent> events) {
            super(id, events);
        }

        @Override
        protected void initializeDefaults() {
            sum = 0;
        }

        public void addNumber(int number) {
            apply (
                    new NumberAddedEvent(number)
            );
        }

        protected void on(NumberAddedEvent event) {
            sum += event.number;
        }

    }

}