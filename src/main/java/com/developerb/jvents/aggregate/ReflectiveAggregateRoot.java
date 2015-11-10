package com.developerb.jvents.aggregate;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 */
public abstract class ReflectiveAggregateRoot<E> {

    private final String id;

    private final List<E> dirtyEvents = new ArrayList<>();

    private long revision = 0;


    public ReflectiveAggregateRoot(String aggregateId, List<E> events) {
        if (aggregateId == null) {
            throw new IllegalArgumentException("Aggregate id can't be null");
        }

        this.id = aggregateId;

        initializeDefaults();
        apply(events, true);
    }

    protected abstract void initializeDefaults();

    protected void apply(E event) {
        apply(Collections.singletonList(event), false);
    }

    private void apply(List<E> events, boolean persisted) {
        for (E event : events) {
            try {
                Class<?> eventType = event.getClass();
                Method[] declaredMethods = this.getClass().getDeclaredMethods();
                Method eventHandlingMethod = null;

                for (Method declaredMethod : declaredMethods) {
                    Class<?>[] parameterTypes = declaredMethod.getParameterTypes();

                    if (parameterTypes.length == 1 && parameterTypes[0].equals(eventType)) {
                        eventHandlingMethod = declaredMethod;
                        break;
                    }
                }

                if (eventHandlingMethod == null) {
                    throw new IllegalStateException("No event handling method for " + eventType + " in " + this.getClass());
                }

                eventHandlingMethod.setAccessible(true);
                eventHandlingMethod.invoke(this, event);


                if (!persisted) {
                    dirtyEvents.add(event);
                }
                else {
                    revision++;
                }
            }
            catch (Exception ex) {
                throw new IllegalStateException("Applying " + event + " to " + this + " failed with an exception", ex);
            }
        }
    }

    public final String id() {
        return id;
    }

    public boolean isDirty() {
        return !dirtyEvents.isEmpty();
    }

    public List<E> dirtyEvents() {
        return dirtyEvents;
    }

    public long currentRevision() {
        return revision;
    }

}
