package com.developerb.jvents.inmemory;

import com.developerb.jvents.AggregateFactory;
import com.developerb.jvents.AppendableEventStore;
import com.developerb.jvents.EventSerializer;
import com.developerb.jvents.EventStore;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 *
 */
public class InMemoryEventStore<E, A> implements EventStore<E, A> {

    private final EventSerializer<E> serializer;

    private final ConcurrentMap<String, List<String>> aggregateStreams = new ConcurrentHashMap<>();

    private final AggregateFactory<A, E> aggregateFactory;


    public InMemoryEventStore(EventSerializer<E> serializer, AggregateFactory<A, E> aggregateFactory) {
        this.serializer = serializer;
        this.aggregateFactory = aggregateFactory;
    }

    @Override
    public <G extends A> G readAggregate(Class<G> type, String aggregateId) {
        synchronized (aggregateStreams) {
            final List<String> strings = aggregateStreams.get(aggregateId);

            if (strings == null) {
                throw new MissingAggregateException(type, aggregateId);
            }

            final List<E> events = strings.stream()
                    .map(serializer::deserialize)
                    .collect(Collectors.toList());

            return aggregateFactory.createInstance(type, aggregateId, events);
        }
    }


    @Override
    public <G extends A> void append(G aggregate) throws AppendFailedException {
        List<E> events = aggregateFactory.getDirtyEvents(aggregate);
        String aggregateId = aggregateFactory.getId(aggregate);
        long expectedRevision = aggregateFactory.currentRevision(aggregate);

        append(aggregateId, expectedRevision, events);
    }


    @Override
    public void append(String aggregateId, long expectedRevision, List<E> events) throws AppendFailedException {
        synchronized (aggregateStreams) {
            if (!aggregateStreams.containsKey(aggregateId)) {
                aggregateStreams.put(aggregateId, new CopyOnWriteArrayList<String>());
            }

            final List<String> existingEvents = aggregateStreams.get(aggregateId);
            long currentRevision = existingEvents.size();

            if (currentRevision != expectedRevision) {
                throw new ConcurrentAggregateModificationException(aggregateId, expectedRevision, currentRevision);
            }

            existingEvents.addAll(events.stream()
                    .map(serializer::serialize)
                    .collect(Collectors.toList()));
        }
    }

}
