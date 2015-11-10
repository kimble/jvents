package com.developerb.jvents.serializer;

/**
 *
 */
public class DeserializationFailedException extends SerializationException {

    private final String serialized;

    public DeserializationFailedException(String serialized, Throwable cause) {
        super("Failed to de-serialize event", cause);
        this.serialized = serialized;
    }

    public String getSerialized() {
        return serialized;
    }

}
