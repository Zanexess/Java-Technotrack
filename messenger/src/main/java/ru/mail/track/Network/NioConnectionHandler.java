package ru.mail.track.Network;

import ru.mail.track.Messeges.MessageBase;
import ru.mail.track.Network.MyNio.InProgress.SocketEvent;
import ru.mail.track.Protocol.Protocol;
import ru.mail.track.Session.Session;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * Класс работающий с сокетом, умеет отправлять данные в сокет
 * Также слушает сокет и рассылает событие о сообщении всем подписчикам (асинхронность)
 */
public class NioConnectionHandler implements ConnectionHandler {

    // подписчики
    private List<MessageListener> listeners = new ArrayList<>();
    private Socket socket;
    private Protocol protocol;
    private Session session;
    private SocketChannel socketChannel;
    private BlockingQueue<MessageBase> inputQueue;

    public NioConnectionHandler(Protocol protocol, Session session, Socket socket, BlockingQueue<MessageBase> inputQueue) throws IOException {
        this.protocol = protocol;
        this.socket = socket;
        this.session = session;
        this.inputQueue = inputQueue;
        session.setConnectionHandler(this);
        socketChannel = socket.getChannel();
        System.out.println("0");
    }

    public void send(MessageBase msg) throws IOException {
        try {
            inputQueue.put(msg);
        } catch (InterruptedException e){

        }
//        System.out.println("3");
//        ByteBuffer byteBuffer = ByteBuffer.wrap(protocol.encode(msg));
//        System.out.println("4");
//        socketChannel.write(byteBuffer);
//        System.out.println("5");
    }

    // Добавить еще подписчика
    public void addListener(MessageListener listener) {}


    public void run() {

    }

    public void stop() {}

    public Session getSession() {
        return session;
    }
}