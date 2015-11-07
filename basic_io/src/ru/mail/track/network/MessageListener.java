package ru.mail.track.network;

import ru.mail.track.data.Message;

/**
 * Слушает сообщения
 */
public interface MessageListener {
    void onMessage(Message message);
}