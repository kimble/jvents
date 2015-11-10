package com.developerb.jvents;

/**
 * Consumers of this library can roll their own serialization
 * implementation if they aren't happy with those supplied by
 * this library.
 *
 * @param <E> Event base type
 */
public interface EventSerializer<E> {

    String serialize(E event);

    E deserialize(String dehydratedEvent);

}
