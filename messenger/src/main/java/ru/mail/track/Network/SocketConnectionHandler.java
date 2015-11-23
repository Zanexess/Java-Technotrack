package ru.mail.track.Network;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.mail.track.Protocol.Protocol;
import ru.mail.track.Messeges.MessageBase;
import ru.mail.track.Session.Session;

/**
 * Класс работающий с сокетом, умеет отправлять данные в сокет
 * Также слушает сокет и рассылает событие о сообщении всем подписчикам (асинхронность)
 */
public class SocketConnectionHandler implements ConnectionHandler {

    // подписчики
    private List<MessageListener> listeners = new ArrayList<>();
    private Socket socket;
    private InputStream in;
    private OutputStream out;
    private Protocol protocol;
    private Session session;

    public SocketConnectionHandler(Protocol protocol, Session session, Socket socket) throws IOException {
        this.protocol = protocol;
        this.socket = socket;
        this.session = session;
        session.setConnectionHandler(this);
        in = socket.getInputStream();
        out = socket.getOutputStream();
    }

    public void send(MessageBase msg) throws IOException {
        out.write(protocol.encode(msg));
        out.flush();
    }

    // Добавить еще подписчика
    public void addListener(MessageListener listener) {
        listeners.add(listener);
    }


    // Разослать всем
    public void notifyListeners(MessageBase msg) {
        for (MessageListener it : listeners){
            it.onMessage(session, msg);
        }
    }

    public void run() {
        final byte[] buf = new byte[1024 * 64];
        while (!Thread.currentThread().isInterrupted()) {
            try {
                int read = in.read(buf);
                if (read > 0) {
                    byte[] bytes = Arrays.copyOf(buf, read);
                    MessageBase msg = protocol.decode(bytes);
                    notifyListeners(msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }

    public void stop() {
        Thread.currentThread().interrupt();
    }

    public Session getSession() {
        return session;
    }
}