package ru.mail.track.Network.NettyTest.NettyWorking.Server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.compression.ZlibCodecFactory;
import io.netty.handler.codec.compression.ZlibWrapper;
import ru.mail.track.Comands.CommandHandler;
import ru.mail.track.Protocol.MyProtocol;
import ru.mail.track.Protocol.Protocol;
import ru.mail.track.Session.SessionManager;


public class ServerInitializer extends ChannelInitializer<SocketChannel>{
    private SessionManager sessionManager;
    private CommandHandler commandHandler;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        Protocol protocol = new MyProtocol();

        pipeline.addLast("decoder", new NettyDecode(protocol));
        pipeline.addLast("encoder", new NettyEncode(protocol));
        pipeline.addLast("handler", new ServerHandler(sessionManager, commandHandler));
    }

    public ServerInitializer(SessionManager sessionManager, CommandHandler commandHandler) {
        this.sessionManager = sessionManager;
        this.commandHandler = commandHandler;
    }

}
