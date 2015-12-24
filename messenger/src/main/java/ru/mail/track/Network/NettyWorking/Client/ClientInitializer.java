//package ru.mail.track.Network.NettyWorking.Client;
//
//import io.netty.channel.ChannelInitializer;
//import io.netty.channel.ChannelPipeline;
//import io.netty.channel.socket.SocketChannel;
//import ru.mail.track.Network.NettyWorking.Server.NettyDecode;
//import ru.mail.track.Network.NettyWorking.Server.NettyEncode;
//import ru.mail.track.Protocol.MyProtocol;
//import ru.mail.track.Protocol.Protocol;
//
//
//public class ClientInitializer extends ChannelInitializer<SocketChannel> {
//    @Override
//    protected void initChannel(SocketChannel socketChannel) throws Exception {
//        ChannelPipeline pipeline = socketChannel.pipeline();
//        Protocol protocol = new MyProtocol();
//
//        pipeline.addLast("decoder", new NettyDecode(protocol));
//        pipeline.addLast("encoder", new NettyEncode(protocol));
//        pipeline.addLast("handler", new ClientHandler());
//    }
//}
