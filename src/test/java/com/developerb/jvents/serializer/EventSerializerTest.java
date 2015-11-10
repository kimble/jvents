package com.developerb.jvents.serializer;

import com.developerb.jvents.EventSerializer;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
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

    public static abstract class TestEvent {

        @Override
        @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
        public boolean equals(Object o) {
            return EqualsBuilder.reflectionEquals(this, o, false);
        }

        @Override
        public int hashCode() {
            return HashCodeBuilder.reflectionHashCode(this, false);
        }

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
        }
    }

}
