package com.developerb.jvents.serializer;

import com.developerb.jvents.EventSerializer;
import com.developerb.jvents.TestEvent;

/**
 *
 */
public class GsonEventSerializerTest extends EventSerializerTest {

    @Override
    protected EventSerializer<TestEvent> createInstanceUnderTest() {
        return new GsonEventSerializer<>();
    }

}