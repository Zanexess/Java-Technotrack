package ru.mail.track.Network;

import io.netty.channel.Channel;
import ru.mail.track.Messeges.MessageBase;
import ru.mail.track.Protocol.Protocol;
import ru.mail.track.Session.Session;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.*;

/**
 * Класс работающий с сокетом, умеет отправлять данные в сокет
 * Также слушает сокет и рассылает событие о сообщении всем подписчикам (асинхронность)
 */
public class NettyConnectionHandler implements ConnectionHandler {
    private Session session;
    private Channel channel;

    public NettyConnectionHandler(Session session, Channel channel) throws IOException {
        this.session = session;
        this.channel = channel;
        session.setConnectionHandler(this);
    }

    public void send(MessageBase msg) throws IOException {
        //TODO FLUSH
        channel.writeAndFlush(msg);
    }

    public void addListener(MessageListener listener) { }

    public void run() {
//        while (!Thread.currentThread().isInterrupted()) {
//
//        }
    }

    public void stop() {
//        Thread.currentThread().interrupt();
    }

}