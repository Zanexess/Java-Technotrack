package ru.mail.track.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import ru.mail.track.Messeges.MessageType;
import ru.mail.track.authorization.AuthorizationService;
import ru.mail.track.authorization.UserStore;
import ru.mail.track.authorization.UserStoreStub;
import ru.mail.track.comands.*;
import ru.mail.track.Messeges.MessageBase;
import ru.mail.track.data.MessageStore;
import ru.mail.track.data.MessageStoreStub;
import ru.mail.track.session.Session;


public class ThreadedServer implements MessageListener {

    public static final int PORT = 19000;
    private volatile boolean isRunning;
    private Map<Long, ConnectionHandler> handlers = new HashMap<Long, ConnectionHandler>();
    private AtomicLong internalCounter = new AtomicLong(0);
    private ServerSocket sSocket;
    private Protocol protocol;
    private SessionManager sessionManager;
    private CommandHandler commandHandler;

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
        while (isRunning) {
            Socket socket = sSocket.accept();
            ConnectionHandler handler = new SocketConnectionHandler(protocol, sessionManager.createSession(), socket);
            handler.addListener(this);

            handlers.put(internalCounter.incrementAndGet(), handler);
            Thread thread = new Thread(handler);
            thread.start();
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

        UserStore userStore = new UserStoreStub();
        MessageStore messageStore = new MessageStoreStub();
        AuthorizationService authorizationService = new AuthorizationService(userStore);

        Map<MessageType, Command> cmds = new HashMap<MessageType, Command>();
        cmds.put(MessageType.MSG_LOGIN, new LoginCommand(authorizationService, sessionManager));
        cmds.put(MessageType.MSG_LOGOUT, new ExitCommand());
        cmds.put(MessageType.MSG_REGISTER, new RegistrationCommand(authorizationService, sessionManager));
        cmds.put(MessageType.MSG_INFO, new SessionInfoCommand(userStore));
        cmds.put(MessageType.MSG_HELP, new HelpCommand(cmds));
        cmds.put(MessageType.MSG_USER, new UserCommand());
        cmds.put(MessageType.MSG_USERPASS, new UserPassCommand());
        //cmds.put(CommandType.MSG_SEND, new SendCommand(sessionManager, messageStore));
        CommandHandler handler = new CommandHandler(cmds);

        ThreadedServer server = new ThreadedServer(protocol, sessionManager, handler);

        try {
            server.startServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
