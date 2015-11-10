package ru.mail.track.data;


import com.sun.xml.internal.ws.server.sei.ValueGetter;
import ru.mail.track.Messeges.MessageBase;
import ru.mail.track.session.User;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 */
public class MessageStoreStub implements MessageStore {

    public static final AtomicLong counter = new AtomicLong(4);
    static long id = 5;


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

    static {

        Chat chat1 = new Chat();
        chat1.addParticipant(0L);
        chat1.addParticipant(2L);

        Chat chat2 = new Chat();
        chat2.addParticipant(0L);
        chat2.addParticipant(1L);
        chat2.addParticipant(2L);
        chat2.addParticipant(3L);

        Chat chat3 = new Chat();
        chat3.addParticipant(2L);
        chat3.addParticipant(3L);

        Chat chat4 = new Chat();
        chat4.addParticipant(0L);

        chats.put(1L, chat1);
        chats.put(2L, chat2);
        chats.put(3L, chat3);
        chats.put(4L, chat4);
    }

    public void addChat(Chat chat){
        chats.put(id, chat);
        id++;
    }

    public void addMessage(Long messageId, Long chatId) {
        chats.get(chatId).addMessage(messageId);
    }

    public List<Long> getParticipantByChatId(Long chatId) {
        return chats.get(chatId).getParticipantIds();
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

    public void addUserToChat(Long userId, Long chatId) {
        chats.get(chatId).addParticipant(userId);
    }

    public List<Long> getChatsByUserId(Long userId){
        List<Long> ch = new ArrayList<Long>();
        for (long i = 1; i <= chats.size(); i++){
            if (chats.get(i).getParticipantIds().contains(userId))
                ch.add(i);
        }
        return ch;
    }

    public void addMessage(Message msg){
        messages.put(msg.getMessageId(), msg);
    }

    public Message getMessagesById(Long messageId){
        return messages.get(messageId);
    }
}