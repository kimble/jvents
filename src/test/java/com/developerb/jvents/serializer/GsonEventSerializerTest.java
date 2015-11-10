package com.developerb.jvents.serializer;

import com.developerb.jvents.EventSerializer;

import static org.junit.Assert.*;

/**
 *
 */
public class GsonEventSerializerTest extends EventSerializerTest {

    @Override
    protected EventSerializer<TestEvent> createInstanceUnderTest() {
        return new GsonEventSerializer<TestEvent>();
    }

}