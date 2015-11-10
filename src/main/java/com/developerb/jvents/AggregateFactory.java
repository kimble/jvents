package com.developerb.jvents;

import java.util.List;

/**
 * Because this library does not require consumers to inherit from our code
 * they will have to supply an instance of this class telling this library
 * how to create an instance of their aggregate type.
 *
 * @param <A> Aggregate base type
 * @param <E> Event base type
 */
public interface AggregateFactory<A, E> {

    <G extends A> G createInstance(Class<G> type, String aggregateId, List<E> events) throws AggregateCreationFailure;


    class AggregateCreationFailure extends RuntimeException {

        public AggregateCreationFailure(Class<?> type, String aggregateId, Throwable cause) {
            super("Failed to create instance of " + type + " with id " + aggregateId, cause);
        }

        public AggregateCreationFailure(Class<?> type, String aggregateId, String message, Throwable cause) {
            super("Failed to create instance of " + type + " with id " + aggregateId + ": " + message, cause);
        }

    }

}
