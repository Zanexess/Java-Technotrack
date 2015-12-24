//package ru.mail.track.Network.NettyWorking.Server;
//
//import io.netty.channel.Channel;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.ChannelInboundMessageHandlerAdapter;
//import io.netty.channel.group.ChannelGroup;
//import io.netty.channel.group.DefaultChannelGroup;
//import ru.mail.track.Comands.CommandHandler;
//import ru.mail.track.Messeges.MessageBase;
//import ru.mail.track.Messeges.MessageType;
//import ru.mail.track.Network.ConnectionHandler;
//import ru.mail.track.Network.NettyConnectionHandler;
//import ru.mail.track.Session.Session;
//import ru.mail.track.Session.SessionManager;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class ServerHandler extends ChannelInboundMessageHandlerAdapter<MessageBase> {
//    private static final ChannelGroup channels = new DefaultChannelGroup();
//    private Map<Channel, Session> sessionChannels = new HashMap<>();
//    private SessionManager sessionManager;
//    private CommandHandler commandHandler;
//
//
//    @Override
//    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
//        Channel incoming = ctx.channel();
//        //TODO RegistrationSession
//        Session session = sessionManager.createSession();
//        ConnectionHandler handler = new NettyConnectionHandler(session, incoming);
//        sessionChannels.put(incoming, session);
//
////        for (Channel channel : channels) {
////            String[] args = new String[1];
////            args[0] = "[SERVER] - " + incoming.remoteAddress() +  " has joined!\n";
////            MessageBase msg = new MessageBase(MessageType.SRV_SUCCESS, args);
////            channel.write(msg);
////        }
//
//        channels.add(ctx.channel());
//    }
//
//    @Override
//    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
//        Channel incoming = ctx.channel();
//        String[] args = new String[1];
//        args[0] = "[SERVER] - " + incoming.remoteAddress() +  " has joined!\n";
//        sessionChannels.remove(incoming);
//        MessageBase msg = new MessageBase(MessageType.SRV_SUCCESS, args);
//        for (Channel channel : channels) {
//            channel.write(msg);
//        }
//        channels.remove(ctx.channel());
//    }
//
//    @Override
//    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        cause.printStackTrace();
//        ctx.close();
//    }
//
//    @Override
//    public void messageReceived(ChannelHandlerContext channelHandlerContext, MessageBase message) throws Exception {
//        Channel incoming = channelHandlerContext.channel();
//        Session session = sessionChannels.get(incoming);
//        if (session != null) {
//            MessageBase msg = commandHandler.parse(session, message);
//            incoming.write(msg);
//        }
//
////        for (Channel channel: channels) {
////            if (channel != incoming) {
////                channel.write("[" + incoming.remoteAddress() + "] " + message + "\n");
////            } else {
////                channel.write("SUCCESS SEND" + "\n");
////            }
////        }
//    }
//
//    public ServerHandler(SessionManager sessionManager, CommandHandler commandHandler) {
//        this.commandHandler = commandHandler;
//        this.sessionManager = sessionManager;
//    }
//}
