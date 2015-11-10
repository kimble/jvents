package com.developerb.jvents.serializer;

import javax.swing.text.html.HTMLDocument;

/**
 *
 */
public class SerializationException extends RuntimeException {

    public SerializationException(String message) {
        super(message);
    }

    public SerializationException(String message, Throwable cause) {
        super(message, cause);
    }

}
