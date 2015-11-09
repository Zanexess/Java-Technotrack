package ru.mail.track.data;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 */
public class MessageStoreStub implements MessageStore {

    public static final AtomicLong counter = new AtomicLong(0);

    List<Message> messages1 = Arrays.asList(
            new Message(1L, "msg1_1"),
            new Message(1L, "msg1_2"),
            new Message(1L, "msg1_3"),
            new Message(1L, "msg1_4"),
            new Message(1L, "msg1_5")
    );
    List<Message> messages2 = Arrays.asList(
            new Message(2L, "msg2_1"),
            new Message(2L, "msg2_2"),
            new Message(2L, "msg2_3"),
            new Message(2L, "msg2_4"),
            new Message(2L, "msg2_5")
    );

    Map<Long, Message> messages = new HashMap<Long, Message>();

    static Map<Long, Chat> chats = new HashMap<Long, Chat>();

//    static {
//        Chat chat1 = new Chat();
//        chat1.addParticipant(0L);
//        chat1.addParticipant(2L);
//
//        Chat chat2 = new Chat();
//        chat2.addParticipant(1L);
//        chat2.addParticipant(2L);
//        chat2.addParticipant(3L);
//
//        chats.put(1L, chat1);
//        chats.put(2L, chat2);
//    }

    public void addMessage(Long messageId, Long chatId) {

    }

    public List<Long> getChatsByUserId(Long userId) {
        return null;
    }

    public Chat getChatById(Long chatId) {
        return chats.get(chatId);
    }

    public List<Long> getMessagesFromChat(Long chatId) {
        return chats.get(chatId).getMessageIds();
    }

    public Message getMessageById(Long messageId) {
        return messages.get(messageId);
    }

//    public void addMessage(Long chatId, Message message) {
//        message.setId(counter.getAndIncrement());
//        chats.get(chatId).addMessage(message.getId());
//        messages.put(message.getId(), message);
//    }

    public void addUserToChat(Long userId, Long chatId) {

    }
}