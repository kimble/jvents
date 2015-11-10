package com.developerb.jvents;

/**
 *
 * @param <E> Event type
 * @param <A> Aggregate type
 */
public interface EventStore<E, A> extends ReadableEventStore<E, A>, AppendableEventStore<A, E> { }
