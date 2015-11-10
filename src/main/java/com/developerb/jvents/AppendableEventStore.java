package com.developerb.jvents;

import java.util.List;

/**
 *
 */
public interface AppendableEventStore<A, E> {

    /**
     * Append one or more events to an aggregate stream.
     *
     * @param aggregateId Aggregate to append events to
     * @param expectedRevision For pessimistic concurrency
     * @param events To be appended to the aggregate stream
     * @throws AppendFailedException
     */
    void append(String aggregateId, long expectedRevision, List<E> events) throws AppendFailedException;


    <G extends A> void append(G aggregate) throws AppendFailedException;



    class AppendFailedException extends RuntimeException {

        public AppendFailedException() {
        }

        public AppendFailedException(String message) {
            super(message);
        }

    }

    class ConcurrentAggregateModificationException extends AppendFailedException {

        private final String aggregateId;
        private final long expectedRevision;
        private final long actualRevision;

        public ConcurrentAggregateModificationException(String aggregateId, long expectedRevision, long actualRevision) {
            super(String.format("Expected revision %d of aggregate <%s> but found it to be %d", expectedRevision, aggregateId, actualRevision));

            this.aggregateId = aggregateId;
            this.expectedRevision = expectedRevision;
            this.actualRevision = actualRevision;
        }

    }

}
