package ru.mail.track.Network;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mail.track.Protocol.MyProtocol;
import ru.mail.track.Protocol.Protocol;
import ru.mail.track.Messeges.MessageBase;
import ru.mail.track.Messeges.MessageType;
import ru.mail.track.Messeges.Request;
import ru.mail.track.Session.Session;

/**
 * Клиентская часть
 */
public class ThreadClient implements MessageListener {

    public static final int PORT = 19000;
    public static final String HOST = "localhost";
    private Protocol protocol;
    private MessageBase msg;

    ConnectionHandler handler;

    public ThreadClient() {
        init();
    }

    public void init() {
        try {
            protocol = new MyProtocol();
            Socket socket = new Socket(HOST, PORT);
            Session session = new Session();
            handler = new SocketConnectionHandler(protocol, session, socket);
            handler.addListener(this);

            Thread socketHandler = new Thread(handler);
            socketHandler.start();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void processInput(String line) throws IOException {
        msg = null;
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
                default:
                    System.out.println("INVALID_INPUT");
                    break;
            }
        } else {
            System.out.println("INVALID_INPUT");
        }
        if (msg != null) {
            handler.send(msg);
        }
    }

    public void onMessage(Session session, MessageBase msg) {
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

    public static void main(String[] args) throws Exception{
        ThreadClient client = new ThreadClient();

        Scanner scanner = new Scanner(System.in);
        System.out.print("> ");

        while (true) {
            String input = scanner.nextLine();
            if ("q".equals(input)) {
                return;
            }
            client.processInput(input);
        }
    }

}