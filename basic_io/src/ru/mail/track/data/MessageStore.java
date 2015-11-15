package ru.mail.track.data;

import java.util.List;

/**
 * Хранилище информации о сообщениях
 */
public interface MessageStore {

    /**
     получаем список ид пользователей заданного чата
     */
    List<Long> getParticipantByChatId(Long chatId);

    /**
     получить информацию о чате
     */
    Chat getChatById(Long chatId);

    /**
     * Список сообщений из чата
     *
     */
    List<Long> getMessagesFromChat(Long chatId);

    /**
     * Получить информацию о сообщении
     */
    Message getMessageById(Long messageId);

    /**
     * Добавить сообщение в чат
     */
    void addMessage(Long messageId, Long chatId);


    /**
     * Добавить пользователя к чату
     */
    void addUserToChat(Long userId, Long chatId);
    /*
     *Чаты пользователя по id
     */

    List<Long> getChatsByUserId(Long userId);

    void addChat(Chat chat);

    void addMessage(Message msg);



}