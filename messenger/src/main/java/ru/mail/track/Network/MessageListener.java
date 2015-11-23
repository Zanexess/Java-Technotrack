package ru.mail.track.Network;

import ru.mail.track.Messeges.MessageBase;
import ru.mail.track.Session.Session;

/**
 * Слушает сообщения
 */
public interface MessageListener {
    void onMessage(Session session, MessageBase message);
}