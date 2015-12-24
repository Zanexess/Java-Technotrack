package ru.mail.track.Network.NettyTest.NettyWorking.Server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import ru.mail.track.Authorization.AuthorizationService;
import ru.mail.track.Comands.*;
import ru.mail.track.Dao.DaoFactory;
import ru.mail.track.Dao.PostgreSQL.PostgreSQLDaoFactory;
import ru.mail.track.Messeges.MessageType;
import ru.mail.track.Network.Server;
import ru.mail.track.Protocol.MyProtocol;
import ru.mail.track.Protocol.Protocol;
import ru.mail.track.Session.SessionManager;
import ru.mail.track.Stores.MessageDatabaseStore;
import ru.mail.track.Stores.MessageStore;
import ru.mail.track.Stores.UserDatabaseStore;
import ru.mail.track.Stores.UserStore;

import java.util.HashMap;
import java.util.Map;

public class NettyServer implements Server {
    private final int port;

    private Protocol protocol;
    private SessionManager sessionManager;
    private CommandHandler commandHandler;
    private NioServerSocketChannel nioServerSocketChannel;
    private EventLoopGroup workerGroup;
    private EventLoopGroup bossGroup;

    public static void main(String[] args) throws Exception {
        Protocol protocol = new MyProtocol();
        SessionManager sessionManager = new SessionManager();
        DaoFactory daoFactory = new PostgreSQLDaoFactory();
        UserStore userStore = new UserDatabaseStore(daoFactory);
        MessageStore messageStore = new MessageDatabaseStore(daoFactory);
//        UserStore userStore = new UserDatabaseManyConnectionsStore(daoFactory);
//        MessageStore messageStore = new MessageDatabaseManyConnectionsStore(daoFactory);
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
        NettyServer nettyServer = new NettyServer(protocol, sessionManager, handler, 19000);
        try {
            nettyServer.startServer();
        } catch (Exception e) {

        }
    }


    @Override
    public void startServer() throws Exception {
        runServer();
    }

    @Override
    public void destroyServer() {
        ChannelFuture cf = nioServerSocketChannel.close();
        cf.awaitUninterruptibly();
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
    }

    public NettyServer(Protocol protocol, SessionManager sessionManager,
                       CommandHandler commandHandler, int port) {
        this.protocol = protocol;
        this.sessionManager = sessionManager;
        this.commandHandler = commandHandler;
        this.port = port;
    }

    public void runServer() throws Exception{
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();

        nioServerSocketChannel = new NioServerSocketChannel();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap()
                    .group(bossGroup, workerGroup)
                    .channel(nioServerSocketChannel.getClass())
                    .childHandler(new ServerInitializer(sessionManager, commandHandler));
            bootstrap.bind(port).sync().channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
