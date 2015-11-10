package com.developerb.jvents;

/**
 *
 */
public interface ReadableEventStore<E, A> {

    <G extends A> G readAggregate(Class<G> type, String id) throws MissingAggregateException;


    class MissingAggregateException extends RuntimeException {

        public MissingAggregateException(Class<?> type, String aggregateId) {
            super("Didn't find aggregate of type " + type + " with id <" + aggregateId + ">");
        }

    }

}
