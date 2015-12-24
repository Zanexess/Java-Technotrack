package ru.mail.track.Network.NettyTest.NettyWorking.Client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.pool.ChannelPoolHandler;
import ru.mail.track.Network.NettyTest.NettyWorking.Server.NettyDecode;
import ru.mail.track.Network.NettyTest.NettyWorking.Server.NettyEncode;
import ru.mail.track.Protocol.MyProtocol;
import ru.mail.track.Protocol.Protocol;

public class SimpleChannelPoolHandler implements ChannelPoolHandler {
    @Override
    public void channelReleased(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.close();
        channel.close();
    }

    @Override
    public void channelAcquired(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        Protocol protocol = new MyProtocol();

        pipeline.addLast("decoder", new NettyDecode(protocol));
        pipeline.addLast("encoder", new NettyEncode(protocol));
        pipeline.addLast("handler", new ClientHandler());
    }

    @Override
    public void channelCreated(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        Protocol protocol = new MyProtocol();

        pipeline.addLast("decoder", new NettyDecode(protocol));
        pipeline.addLast("encoder", new NettyEncode(protocol));
        pipeline.addLast("handler", new ClientHandler());
    }
}
