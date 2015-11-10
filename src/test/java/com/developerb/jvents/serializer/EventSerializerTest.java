package com.developerb.jvents.serializer;

import com.developerb.jvents.EventSerializer;
import com.developerb.jvents.TestEvent;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 *
 */
public abstract class EventSerializerTest {

    protected EventSerializer<TestEvent> serializer;

    @Before
    public void setupSerializer() {
        serializer = createInstanceUnderTest();
    }

    protected abstract EventSerializer<TestEvent> createInstanceUnderTest();


    @Test
    public void instanceUnderTest() throws Exception {
        assertNotNull("Serializer is null", serializer);
    }

    @Test
    public void serializeThenDeserialize() throws Exception {
        TestEvent event = new SomeEvent("Nasse Nøff", 25);
        String serialized = serializer.serialize(event);

        TestEvent restoredEvent = serializer.deserialize(serialized);
        assertEquals(event, restoredEvent);
    }

    public static class SomeEvent extends TestEvent {

        String name;
        int age;

        public SomeEvent(String name, int age) {
            this.name = name;
            this.age = age;
        }

    }

}
