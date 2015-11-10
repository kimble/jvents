package com.developerb.jvents;

import java.util.List;

/**
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
