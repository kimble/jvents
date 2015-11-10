package com.developerb.jvents.aggregate;

import java.lang.reflect.Method;
import java.util.List;

/**
 *
 */
public class ReflectiveAggregateRoot {

    private final String id;

    public ReflectiveAggregateRoot(String aggregateId, List<?> events) {
        if (aggregateId == null) {
            throw new IllegalArgumentException("Aggregate id can't be null");
        }

        this.id = aggregateId;
        apply(events);
    }


    private void apply(List<?> events) {
        for (Object event : events) {
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
            }
            catch (Exception ex) {
                throw new IllegalStateException("Applying " + event + " to " + this + " failed with an exception", ex);
            }
        }
    }

    public final String getId() {
        return id;
    }

}
