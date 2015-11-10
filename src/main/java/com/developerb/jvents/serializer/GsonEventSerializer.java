package com.developerb.jvents.serializer;

import com.developerb.jvents.EventSerializer;
import com.google.gson.Gson;

/**
 *
 */
public class GsonEventSerializer<E> implements EventSerializer<E> {

    private final Gson gson;

    public GsonEventSerializer() {
        this.gson = new Gson();
    }

    @Override
    public String serialize(E event) throws SerializationFailedException {
        try {
            String type = event.getClass().getName();
            String json = gson.toJson(event);

            return "::" + type + "\n" + json;
        }
        catch (Exception ex) {
            throw new SerializationFailedException(event, ex);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public E deserialize(String dehydratedEvent) throws DeserializationFailedException {
        try {
            if (!dehydratedEvent.startsWith("::")) {
                throw new IllegalArgumentException("Expected string to begin with ::");
            }

            int pos = dehydratedEvent.indexOf('\n');
            String typeName = dehydratedEvent.substring(2, pos);
            String jsonData = dehydratedEvent.substring(pos + 1);

            Class<E> type = (Class<E>) Class.forName(typeName);
            return gson.fromJson(jsonData, type);
        }
        catch (Exception ex) {
            throw new DeserializationFailedException(dehydratedEvent, ex);
        }
    }

}
