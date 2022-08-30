package ru.mail.track.network;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import ru.mail.track.Messeges.MessageBase;
import ru.mail.track.Messeges.MessageType;
import ru.mail.track.data.Chat;
import ru.mail.track.data.Message;
import ru.mail.track.session.Session;

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
        String args[] = line.split(" ");
        MessageBase msg = new MessageBase(MessageType.MSG_ERROR, args);
        // FIXME: то есть сначала вы вызываете args[0].startsWith
        // а потом в каждой ветке проверяете != null - смысла никакого
        // Также лучше воспользоваться оператором switch, if-else здесь не подходит
        if (args[0].startsWith("\\")) {
            if (args[0] != null && args[0].equals("\\exit")) {
                msg = new MessageBase(MessageType.MSG_LOGOUT, args);
            } else if (args[0] != null && args[0].equals("\\login")) {
                msg = new MessageBase(MessageType.MSG_LOGIN, args);
            } else if (args[0] != null && args[0].equals("\\register")) {
                msg = new MessageBase(MessageType.MSG_REGISTER, args);
            } else if (args[0] != null && args[0].equals("\\info")) {
                msg = new MessageBase(MessageType.MSG_INFO, args);
            } else if (args[0] != null && args[0].equals("\\help")) {
                msg = new MessageBase(MessageType.MSG_HELP, args);
            } else if (args[0] != null && args[0].equals("\\user")) {
                msg = new MessageBase(MessageType.MSG_USER, args);
            } else if (args[0] != null && args[0].equals("\\user_pass")) {
                msg = new MessageBase(MessageType.MSG_USERPASS, args);
            } else if (args[0] != null && args[0].equals("\\chat_list")) {
                msg = new MessageBase(MessageType.MSG_CHATLIST, args);
            } else if (args[0] != null && args[0].equals("\\chat_create")) {
                msg = new MessageBase(MessageType.MSG_CHATCREATE, args);
            } else if (args[0] != null && args[0].equals("\\chat_send")) {
                msg = new MessageBase(MessageType.MSG_CHATSEND, args);
            } else if (args[0] != null && args[0].equals("\\chat_history")) {
                msg = new MessageBase(MessageType.MSG_CHATHISTORY, args);
            }
        }
        handler.send(msg);
    }

    public void onMessage(Session session, MessageBase msg) {

        // FIXME: не надо на всякий случай. Если у Вас протокол может вернуть null - то проверяйте
        // если нет, то это только путает

        // FIXME: аналогично - use switch!
        if (msg == null)  //на всякий случай
            System.out.println("NULL");
        else if (msg.getMessageType() == MessageType.SRV_ERROR)
            System.out.println("ERROR: " + msg.getArgs()[0]);
        else if (msg.getMessageType() == MessageType.SRV_SUCCESS) {
            System.out.println("SUCCESS: ");
            for (int i = 0; i < msg.getArgs().length; i++) {
                System.out.print(msg.getArgs()[i] + " ");
                System.out.println();
            }
        } else if (msg.getMessageType() == MessageType.SRV_LOGINERROR) {
            System.out.println("LOGIN ERROR: " + msg.getArgs()[0]);
        } else if (msg.getMessageType() == MessageType.SRV_INVALIDINPUT) {
            System.out.println("INVALID INPUT: " + msg.getArgs()[0]);
        } else if (msg.getMessageType() == MessageType.SRV_NEWMESSAGE) {
            System.out.println("NEWMESSAGE: ");
            for (int i = 0; i < msg.getArgs().length; i++) {
                System.out.print(msg.getArgs()[i] + " ");
                System.out.println();
            }
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