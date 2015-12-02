package ru.mail.track.Network.MyNio.InProgress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mail.track.Authorization.AuthorizationService;
import ru.mail.track.Comands.*;
import ru.mail.track.Dao.DaoFactory;
import ru.mail.track.Dao.PostgreSQL.PostgreSQLDaoFactory;
import ru.mail.track.Messeges.MessageBase;
import ru.mail.track.Messeges.MessageType;
import ru.mail.track.Network.MessageListener;
import ru.mail.track.Protocol.MyProtocol;
import ru.mail.track.Protocol.Protocol;
import ru.mail.track.Session.Session;
import ru.mail.track.Session.SessionManager;
import ru.mail.track.Stores.MessageDatabaseManyConnectionsStore;
import ru.mail.track.Stores.MessageStore;
import ru.mail.track.Stores.UserDatabaseManyConnectionsStore;
import ru.mail.track.Stores.UserStore;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class NioServer implements Runnable {

    public static final int PORT = 19000;
    static Logger log = LoggerFactory.getLogger(NioServer.class);

    private Protocol protocol;
    private SessionManager sessionManager;
    private CommandHandler commandHandler;

    private Selector selector;
    private ServerSocketChannel socketChannel;

    private Map<Socket, Session> socketSessions;
    private Map<Socket, MessageBase> socketResults;

    private ByteBuffer readBuffer = ByteBuffer.allocate(8192);

    private BlockingQueue<SocketEvent> eventQueue = new ArrayBlockingQueue<>(10);
    private ExecutorService service = Executors.newFixedThreadPool(5);

    public static void main(String[] args) throws Exception {
        Protocol protocol = new MyProtocol();
        SessionManager sessionManager = new SessionManager();
        DaoFactory daoFactory = new PostgreSQLDaoFactory();
//        UserStore userStore = new UserDatabaseStore(daoFactory);
//        MessageStore messageStore = new MessageDatabaseStore(daoFactory);
        UserStore userStore = new UserDatabaseManyConnectionsStore(daoFactory);
        MessageStore messageStore = new MessageDatabaseManyConnectionsStore(daoFactory);
        AuthorizationService authorizationService = new AuthorizationService(userStore);

        Map<MessageType, Command> cmds = new HashMap<>();
        cmds.put(MessageType.MSG_LOGIN, new LoginCommand(authorizationService, sessionManager));
        cmds.put(MessageType.MSG_LOGOUT, new ExitCommand());
        cmds.put(MessageType.MSG_REGISTER, new RegistrationCommand(authorizationService, sessionManager));
        cmds.put(MessageType.MSG_INFO, new SessionInfoCommand(userStore));
        cmds.put(MessageType.MSG_HELP, new HelpCommand(cmds));
        cmds.put(MessageType.MSG_USER, new UserCommand(userStore));
        cmds.put(MessageType.MSG_USERPASS, new UserPassCommand(userStore));
        cmds.put(MessageType.MSG_CHATLIST, new ChatListCommand(messageStore));
        cmds.put(MessageType.MSG_CHATCREATE, new ChatCreateCommand(messageStore, userStore));
        cmds.put(MessageType.MSG_CHATSEND, new ChatSendCommand(sessionManager, messageStore));
        cmds.put(MessageType.MSG_CHATHISTORY, new ChatHistoryCommand(messageStore));
        cmds.put(MessageType.MSG_CHATFIND, new ChatFindCommand(messageStore));

        CommandHandler handler = new CommandHandler(cmds);

        NioServer server = new NioServer();
        server.init(protocol, sessionManager, handler);
        System.out.println("Server started");
        Thread t = new Thread(server);
        t.start();
    }


    public void init(Protocol protocol, SessionManager sessionManager, CommandHandler handler) throws Exception {
        selector = Selector.open();
        socketChannel = ServerSocketChannel.open();

        socketChannel.socket().bind(new InetSocketAddress(PORT));
        socketChannel.configureBlocking(false);

        socketChannel.register(selector, SelectionKey.OP_ACCEPT);

        this.protocol = protocol;
        this.sessionManager = sessionManager;
        this.commandHandler = handler;
        socketSessions = new HashMap<>();
        socketResults = new HashMap<>();

        service.submit(() -> {
            try {
                while (true) {
                    SocketEvent event = eventQueue.take();
                    SocketChannel channel = event.getChannel();
                    ByteBuffer buffer = event.getBuffer();
                    Session session = socketSessions.get(channel.socket());


                    MessageBase socketMessage = this.protocol.decode(buffer.array());

                    if (socketMessage != null) {
                        MessageBase result = this.commandHandler.parse(session, socketMessage);
                        if (!socketResults.containsKey(channel.socket())) {
                            socketResults.put(channel.socket(), result);
                        }
                    }

                    SelectionKey key = channel.keyFor(selector);
                    key.interestOps(SelectionKey.OP_WRITE);
                    selector.wakeup();
                }

            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
                System.exit(1);
            }
        });

    }

    @Override
    public void run() {
        while (true) {
            try {
                log.info("On select()");
                int num = selector.select();
                log.info("selected...");
                if (num == 0) {
                    continue;
                }

                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> it = keys.iterator();
                while (it.hasNext()) {
                    SelectionKey key = it.next();
                    it.remove();

                    if (!key.isValid()){
                        continue;
                    } else if (key.isAcceptable()) {
                        log.info("[acceptable] {}", key.hashCode());

                        SocketChannel socketChannel = ((ServerSocketChannel) key.channel()).accept();
                        Socket socket = socketChannel.socket();

                        socketChannel.configureBlocking(false);

                        Session session = sessionManager.createSession();

                        socketSessions.put(socket, session);
                        log.info("accepted on {}", socketChannel.getLocalAddress());

                        socketChannel.register(selector, SelectionKey.OP_READ);
                    } else if (key.isReadable()) {
                        log.info("[readable {}", key.hashCode());
                        SocketChannel socketChannel = (SocketChannel) key.channel();

                        readBuffer.clear();
                        int numRead;
                        try {
                            numRead = socketChannel.read(readBuffer);
                        } catch (IOException e) {
                            log.error("Failed to read data from channel", e);

                            // The remote forcibly closed the connection, cancel
                            // the selection key and close the channel.
                            key.cancel();
                            socketChannel.close();
                            break;
                        }

                        if (numRead == -1) {
                            log.error("Failed to read data from channel (-1)");

                            // Remote entity shut the socket down cleanly. Do the
                            // same from our end and cancel the channel.
                            key.channel().close();
                            key.cancel();
                            break;
                        }

                        log.info("read: {}", readBuffer.toString());
                        readBuffer.flip();

                        SocketEvent event = new SocketEvent();
                        event.setChannel(socketChannel);
                        event.setBuffer((ByteBuffer) readBuffer.flip());
                        try {
                            eventQueue.put(event);
                        } catch (InterruptedException e) {
                            System.err.println(e.getMessage());
                            System.exit(1);
                        }

                    } else if (key.isWritable()) {
                        log.info("[writable]{}", key.hashCode());
                        SocketChannel socketChannel = (SocketChannel) key.channel();

                        if (socketResults.containsKey(socketChannel.socket())) {
                            MessageBase message = socketResults.get(socketChannel.socket());
                            byte[] encodedMessage = protocol.encode(message);
                            if (encodedMessage != null) {
                                ByteBuffer buf = ByteBuffer.wrap(encodedMessage);
                                socketChannel.write(buf);
                                buf = null;
                            }
                            socketResults.remove(socketChannel.socket());
                        }

                        key.interestOps(SelectionKey.OP_READ);
                    }
                }
                keys.clear();
            } catch (IOException e) {
                System.exit(1);
            }
        }
    }
}
