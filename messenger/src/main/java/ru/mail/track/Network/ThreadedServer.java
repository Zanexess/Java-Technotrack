package ru.mail.track.Network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mail.track.ConnectionPool.Network.ThreadPool;
import ru.mail.track.Protocol.MyProtocol;
import ru.mail.track.Protocol.Protocol;
import ru.mail.track.Messeges.MessageType;
import ru.mail.track.Session.SessionManager;
import ru.mail.track.Authorization.AuthorizationService;
import ru.mail.track.Stores.*;
import ru.mail.track.Comands.*;
import ru.mail.track.Messeges.MessageBase;
import ru.mail.track.Dao.DaoFactory;
import ru.mail.track.Dao.PostgreSQL.PostgreSQLDaoFactory;
import ru.mail.track.Session.Session;


public class ThreadedServer implements MessageListener {

    public static final int PORT = 19000;
    private volatile boolean isRunning;
    private Map<Long, ConnectionHandler> handlers = new HashMap<>();
    private AtomicLong internalCounter = new AtomicLong(0);
    private ServerSocket sSocket;
    private Protocol protocol;
    private SessionManager sessionManager;
    private CommandHandler commandHandler;
    static Logger log = LoggerFactory.getLogger(ThreadedServer.class);

    public ThreadedServer(Protocol protocol, SessionManager sessionManager, CommandHandler commandHandler) {
        this.protocol = protocol;
        this.sessionManager = sessionManager;
        this.commandHandler = commandHandler;

        try {
            sSocket = new ServerSocket(PORT);
            sSocket.setReuseAddress(true);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }



    private void startServer() throws Exception {
        isRunning = true;

        //ThreadPool threadPool = new ThreadPool(1, 3);

        while (isRunning) {
            Socket socket = sSocket.accept();
            ConnectionHandler handler = new SocketConnectionHandler(protocol, sessionManager.createSession(), socket);
            handler.addListener(this);

            handlers.put(internalCounter.incrementAndGet(), handler);
            Thread thread = new Thread(handler);
            thread.start();

            //threadPool.execute(new Thread(handler));


        }
    }


    public void stopServer() {
        isRunning = false;
        for (ConnectionHandler handler : handlers.values()) {
            handler.stop();
        }
    }

    public void onMessage(Session session, MessageBase message) {
        commandHandler.onMessage(session, message);
    }

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
        cmds.put(MessageType.MSG_CHATCREATE, new ChatCreateCommand(messageStore));
        cmds.put(MessageType.MSG_CHATSEND, new ChatSendCommand(sessionManager, messageStore));
        cmds.put(MessageType.MSG_CHATHISTORY, new ChatHistoryCommand(messageStore));
        cmds.put(MessageType.MSG_CHATFIND, new ChatFindCommand(messageStore));

        CommandHandler handler = new CommandHandler(cmds);

        ThreadedServer server = new ThreadedServer(protocol, sessionManager, handler);
        log.info("All services is configure");

        try {
            server.startServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
