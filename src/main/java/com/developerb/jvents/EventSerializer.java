package com.developerb.jvents;

/**
 *
 * @param <E> Event base type
 */
public interface EventSerializer<E> {

    String serialize(E event);

    E deserialize(String dehydratedEvent);

}
