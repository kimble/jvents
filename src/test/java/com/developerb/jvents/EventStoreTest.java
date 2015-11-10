package com.developerb.jvents;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;



public abstract class EventStoreTest {

    private EventStore<TestEvent, TestAggregate> store;

    @Before
    public final void initializeEventStore() throws Exception {
        store = createEventStoreUnderTest();
    }

    protected abstract EventStore<TestEvent, TestAggregate> createEventStoreUnderTest();


    @Test
    public void appendFirstEventThenReadBack() throws Exception {
        store.append("aggregate-x", 0, Collections.singletonList (
                new ParticipantEntered("Nasse Nøff"))
        );

        ChatAggregate aggregate = store.readAggregate(ChatAggregate.class, "aggregate-x");

        assertNotNull(aggregate);
        assertEquals("aggregate-x", aggregate.id());
    }

    @Test
    public void flushDirtyEvents() throws Exception {
        ChatAggregate chat = new ChatAggregate("hundremeterskogen");
        chat.enter("Nasse Nøff");
        chat.enter("Ole Brumm");
        chat.chat("Nasse Nøff", "Hei på deg Ole Brumm!");

        assertEquals(Collections.singletonList("Nasse Nøff: Hei på deg Ole Brumm!"), chat.messages);

        store.append(chat);

        ChatAggregate restoredChat = store.readAggregate(ChatAggregate.class, "hundremeterskogen");
        assertEquals(Collections.singletonList("Nasse Nøff: Hei på deg Ole Brumm!"), restoredChat.messages);
        restoredChat.chat("Ole Brumm", "Hallo Nøff!");

        store.append(restoredChat);

        restoredChat = store.readAggregate(ChatAggregate.class, "hundremeterskogen");

        assertEquals(Arrays.asList (
                    "Nasse Nøff: Hei på deg Ole Brumm!",
                    "Ole Brumm: Hallo Nøff!"
                ),

                restoredChat.messages
        );
    }



    public static class ParticipantEntered extends TestEvent {

        String name;

        public ParticipantEntered(String name) {
            this.name = name;
        }

    }

    public static class ParticipantChatted extends TestEvent {

        String name;
        String message;

        public ParticipantChatted(String name, String message) {
            this.name = name;
            this.message = message;
        }

    }

    public static class ChatAggregate extends TestAggregate {

        public Set<String> participants;
        public List<String> messages;

        public ChatAggregate(String id) {
            super(id);
        }

        public ChatAggregate(String id, List<TestEvent> events) {
            super(id, events);
        }

        @Override
        protected void initializeDefaults() {
            participants = new HashSet<>();
            messages = new ArrayList<>();
        }

        // Do

        public void enter(String name) {
            apply(new ParticipantEntered(name));
        }

        public void chat(String name, String message) {
            apply(new ParticipantChatted(name, message));
        }

        // Apply

        protected void on(ParticipantEntered event) {
            participants.add(event.name);
        }

        protected void on(ParticipantChatted event) {
            messages.add(event.name + ": " + event.message);
        }

    }

}
