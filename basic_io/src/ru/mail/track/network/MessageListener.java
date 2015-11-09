package ru.mail.track.network;

import ru.mail.track.Messeges.MessageBase;
import ru.mail.track.session.Session;

/**
 * Слушает сообщения
 */
public interface MessageListener {
    void onMessage(Session session, MessageBase message);
}