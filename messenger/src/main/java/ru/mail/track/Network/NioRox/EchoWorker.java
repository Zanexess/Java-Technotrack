package ru.mail.track.Network.NioRox;

import java.nio.channels.SocketChannel;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by zanexess on 01.12.15.
 */
public class EchoWorker implements Runnable {
    //TODO blocking queue

    private List queue = new LinkedList<>();

    public void processData(NioServer server, SocketChannel socket, byte[] data, int count) {
        byte[] dataCopy = new byte[count];
        System.arraycopy(data, 0, dataCopy, 0, count);
        synchronized (queue) {
            //TODO ServerDataEvent??
            queue.add(new ServerDataEvent(server, socket, dataCopy));
            queue.notify();
        }
    }

    @Override
    public void run() {
        ServerDataEvent dataEvent = null;

        while (true) {
            synchronized (queue) {
                while (queue.isEmpty()) {
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {

                    }
                    dataEvent = (ServerDataEvent) queue.remove(0);
                }
            }
            dataEvent.server.send(dataEvent.socket, dataEvent.data);
        }
    }
}
