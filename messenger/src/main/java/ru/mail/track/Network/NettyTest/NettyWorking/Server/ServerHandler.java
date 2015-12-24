package ru.mail.track.Network.NettyTest.NettyWorking.Server;

import io.netty.channel.*;
//import io.netty.channel.ChannelInboundMessageHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import ru.mail.track.Comands.CommandHandler;
import ru.mail.track.Messeges.MessageBase;
import ru.mail.track.Messeges.MessageType;
import ru.mail.track.Network.ConnectionHandler;
import ru.mail.track.Network.NettyConnectionHandler;
import ru.mail.track.Session.Session;
import ru.mail.track.Session.SessionManager;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServerHandler extends SimpleChannelInboundHandler<MessageBase> {
    private static final ChannelGroup channels = new DefaultChannelGroup("EventExecutor", GlobalEventExecutor.INSTANCE);
    private Map<Channel, Session> sessionChannels = new HashMap<>();
    private SessionManager sessionManager;
    private CommandHandler commandHandler;

//    @Override
//    protected void messageReceived(ChannelHandlerContext channelHandlerContext, MessageBase messageBase) throws Exception {
//        Channel incoming = channelHandlerContext.channel();
//        Session session = sessionChannels.get(incoming);
//        if (session != null) {
//            MessageBase msg = commandHandler.parse(session, messageBase);
//            incoming.writeAndFlush(msg);
//        }
//    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageBase messageBase) throws Exception {
        Channel incoming = channelHandlerContext.channel();
        Session session = sessionChannels.get(incoming);
        if (session != null) {
            MessageBase msg = commandHandler.parse(session, messageBase);
            incoming.writeAndFlush(msg);
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        //TODO RegistrationSession
        Session session = sessionManager.createSession();
        ConnectionHandler handler = new NettyConnectionHandler(session, incoming);
        sessionChannels.put(incoming, session);
        channels.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        String[] args = new String[1];
        args[0] = "[SERVER] - " + incoming.remoteAddress() +  " has left!\n";
        sessionChannels.remove(incoming);
        MessageBase msg = new MessageBase(MessageType.SRV_SUCCESS, args);
        for (Channel channel : channels) {
            channel.writeAndFlush(msg);
        }
        channels.remove(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    public ServerHandler(SessionManager sessionManager, CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
        this.sessionManager = sessionManager;
    }
}
