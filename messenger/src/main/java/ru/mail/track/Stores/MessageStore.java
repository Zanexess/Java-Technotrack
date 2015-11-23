package ru.mail.track.Stores;
import ru.mail.track.Data.Chat;
import ru.mail.track.Data.Message;

import java.util.List;

/**
 * Хранилище информации о сообщениях
 */
public interface MessageStore {

    List<Long> getParticipantByChatId(Long chatId);

    Chat getChatById(Long chatId);

    List<Long> getMessagesFromChat(Long chatId);

    Message getMessageById(Long messageId);

    List<Long> getChatsByUserId(Long userId);

    void addChat(Chat chat);

    void addMessage(Message msg);
}