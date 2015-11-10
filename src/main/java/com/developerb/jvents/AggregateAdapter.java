package com.developerb.jvents;


/**
 *
 * @param <A> Aggregate base type
 * @param <I> Aggregate id type
 */
public interface AggregateAdapter<A, I> {

    I getId(A aggregate);

}
