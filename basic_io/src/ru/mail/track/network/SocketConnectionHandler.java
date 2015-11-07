package ru.mail.track.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.mail.track.data.Message;
import ru.mail.track.network.ConnectionHandler;
import ru.mail.track.network.MessageListener;
import ru.mail.track.network.Protocol;


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

    public SocketConnectionHandler(Socket socket) throws IOException {
        this.socket = socket;
        in = socket.getInputStream();
        out = socket.getOutputStream();
    }

    @Override
    public void send(Message msg) throws IOException {
        // TODO: здесь должен быть встроен алгоритм кодирования/декодирования сообщений
        // то есть требуется описать протокол
        out.write(Protocol.encode(msg));
        out.flush();
    }

    // Добавить еще подписчика
    @Override
    public void addListener(MessageListener listener) {
        listeners.add(listener);
    }


    // Разослать всем
    public void notifyListeners(Message msg) {
        listeners.forEach(it -> it.onMessage(msg));
    }

    @Override
    public void run() {
        final byte[] buf = new byte[1024 * 64];
        while (!Thread.currentThread().isInterrupted()) {
            try {
                int read = in.read(buf);
                if (read > 0) {
                    Message msg = Protocol.decode(Arrays.copyOf(buf, read));

                    // Уведомим всех подписчиков этого события
                    notifyListeners(msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public void stop() {
        Thread.currentThread().interrupt();
    }
}