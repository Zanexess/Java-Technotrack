package ru.mail.track.network;

import java.io.IOException;

import ru.mail.track.Messeges.MessageBase;

/**
 * Обработчик сокета
 */
public interface ConnectionHandler extends Runnable {

    void send(MessageBase msg) throws IOException;

    void addListener(MessageListener listener);

    void stop();
}
