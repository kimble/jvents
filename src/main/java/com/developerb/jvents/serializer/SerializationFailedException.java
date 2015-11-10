package com.developerb.jvents.serializer;

import javax.swing.text.html.HTMLDocument;

/**
 *
 */
public class SerializationFailedException extends RuntimeException {

    public SerializationFailedException(Object event, Throwable cause) {
        super("Failed to serialize: " + event, cause);
    }

}
