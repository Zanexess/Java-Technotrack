package ru.mail.track.Network.MyNio.InProgress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.mail.track.Messeges.MessageBase;
import ru.mail.track.Messeges.MessageType;
import ru.mail.track.Messeges.Request;
import ru.mail.track.Network.ConnectionHandler;
import ru.mail.track.Protocol.MyProtocol;
import ru.mail.track.Protocol.Protocol;
import ru.mail.track.Session.Session;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class NioClient {

    public static final int PORT = 19000;
    public static final String HOST = "localhost";
    static Logger log = LoggerFactory.getLogger(NioClient.class);
    private Protocol protocol;

    private Selector selector;
    private SocketChannel channel;
    private ByteBuffer buffer = ByteBuffer.allocate(8192);
    private BlockingQueue<MessageBase> inputQueue = new ArrayBlockingQueue<>(10);
    ConnectionHandler handler;

    public void init() {
        try {
            protocol = new MyProtocol();
            Socket socket = new Socket(HOST, PORT);
            Session session = new Session();
            //TODO ConnectionHandler
            //handler = new SocketConnectionHandler(protocol, session, socket);
            handler = new NioConnectionHandler(protocol, session, socket, inputQueue);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public NioClient() {
        init();
    }



    public static void main(String[] args) throws Exception {
        NioClient client = new NioClient();
        client.start();
    }

    private void start() throws IOException, InterruptedException {
        Thread consoleHandler = new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            System.out.println(">");
            while (true) {
                try {
                    if (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        if ("q".equals(line)) {
                            //TODO EndProcess
                            System.exit(0);
                        }

                        try {
                            MessageBase msg = this.processInput(line);
                            if (msg != null) {
                                inputQueue.put(msg);
                            } else {
                                System.out.println("INVALID INPUT");
                            }
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }

                    }

                    SelectionKey key = channel.keyFor(selector);
                    log.info("wake up: {}", key.hashCode());
                    key.interestOps(SelectionKey.OP_WRITE);
                    selector.wakeup();
                } catch (InterruptedException e) {
                    System.err.println(e.getMessage());
                    System.exit(1);
                }
            }
        });

        consoleHandler.start();

        selector = Selector.open();
        channel = SocketChannel.open();
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_CONNECT);

        channel.connect(new InetSocketAddress(HOST, PORT));

        while (true) {
            log.info("Waiting on select()...");
            int num = selector.select();
            log.info("Raised {} events", num);


            Set<SelectionKey> keys = selector.selectedKeys();
            for (SelectionKey sKey : keys) {
                if (sKey.isConnectable()) {
                    channel.finishConnect();
                    sKey.interestOps(SelectionKey.OP_WRITE);
                } else if (sKey.isReadable()) {
                    log.info("[readable]");

                    buffer.clear();
                    int numRead = channel.read(buffer);
                    if (numRead < 0) {
                        break;
                    }

                    MessageBase serverResponse = protocol.decode(buffer.array());
                    processServerResponse(serverResponse);

                } else if (sKey.isWritable()) {
                    log.info("[writable]");
                    MessageBase message = inputQueue.poll();
                    if (message != null) {
                        byte[] encodedMessage = protocol.encode(message);
                        if (encodedMessage != null) {
                            channel.write(ByteBuffer.wrap(encodedMessage));
                        }
                    }

                    sKey.interestOps(SelectionKey.OP_READ);
                }
            }
        }
    }

    private void processServerResponse(MessageBase serverResponse) {
        if (serverResponse != null) {
            MessageBase responseMessage = serverResponse;
            if (responseMessage != null) {
                outputParsing(responseMessage);
            } else {
                System.out.println("Error: can't decode server response.");
            }
        } else {
            System.out.println("Wrong server response");
        }
    }


    public MessageBase processInput(String line) throws IOException {
        MessageBase msg = null;
        String args[] = line.split(" ");
        if (args[0].startsWith("\\")) {
            Request request = Request.getType(args[0]);
            switch (request) {
                case REQUEST_EXIT:
                    msg = new MessageBase(MessageType.MSG_LOGOUT, args);
                    break;
                case REQUEST_LOGIN:
                    msg = new MessageBase(MessageType.MSG_LOGIN, args);
                    break;
                case REQUEST_REGISTER:
                    msg = new MessageBase(MessageType.MSG_REGISTER, args);
                    break;
                case REQUEST_INFO:
                    msg = new MessageBase(MessageType.MSG_INFO, args);
                    break;
                case REQUEST_HELP:
                    msg = new MessageBase(MessageType.MSG_HELP, args);
                    break;
                case REQUEST_USER:
                    msg = new MessageBase(MessageType.MSG_USER, args);
                    break;
                case REQUEST_USERPASS:
                    msg = new MessageBase(MessageType.MSG_USERPASS, args);
                    break;
                case REQUEST_CHATLIST:
                    msg = new MessageBase(MessageType.MSG_CHATLIST, args);
                    break;
                case REQUEST_CHATCREATE:
                    msg = new MessageBase(MessageType.MSG_CHATCREATE, args);
                    break;
                case REQUEST_CHATSEND:
                    msg = new MessageBase(MessageType.MSG_CHATSEND, args);
                    break;
                case REQUEST_CHATHISTORY:
                    msg = new MessageBase(MessageType.MSG_CHATHISTORY, args);
                    break;
                case REQUEST_CHATFIND:
                    msg = new MessageBase(MessageType.MSG_CHATFIND, args);
                    break;
            }
        }
        return msg;
    }

    public void outputParsing(MessageBase msg) {
        switch (msg.getMessageType()) {
            case SRV_ERROR:
                System.out.println("ERROR: " + msg.getArgs()[0]);
                break;
            case SRV_SUCCESS:
                System.out.println("SUCCESS: ");
                for (int i = 0; i < msg.getArgs().length; i++) {
                    System.out.print(msg.getArgs()[i] + " ");
                    System.out.println();
                }
                break;
            case SRV_LOGINERROR:
                System.out.println("LOGIN ERROR: " + msg.getArgs()[0]);
                break;
            case SRV_INVALIDINPUT:
                System.out.println("INVALID INPUT: " + msg.getArgs()[0]);
                break;
            case SRV_NEWMESSAGE:
                System.out.println("NEWMESSAGE: ");
                for (int i = 0; i < msg.getArgs().length; i++) {
                    System.out.print(msg.getArgs()[i] + " ");
                    System.out.println();
                }
                break;
        }
    }
}
