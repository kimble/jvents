package com.developerb.jvents.aggregate;

import com.developerb.jvents.AggregateFactory;

import java.lang.reflect.Constructor;
import java.util.List;

/**
 *
 */
public abstract class ReflectiveAggregateFactory<A, E> implements AggregateFactory<A, E> {

    @Override
    public <G extends A> G createInstance(Class<G> type, String aggregateId, List<E> events) throws AggregateCreationFailure {
        try {
            final Constructor<G> constructor = type.getDeclaredConstructor(String.class, List.class);
            return constructor.newInstance(aggregateId, events);
        }
        catch (NoSuchMethodException ex) {
            throw new AggregateCreationFailure(type, aggregateId, "Missing appropriate constructor (String aggregateId, List<E> events)", ex);
        }
        catch (Exception ex) {
            throw new AggregateCreationFailure(type, aggregateId, ex);
        }
    }

}
