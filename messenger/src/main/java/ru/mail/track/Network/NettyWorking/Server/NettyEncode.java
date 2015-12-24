//package ru.mail.track.Network.NettyWorking.Server;
//
//import io.netty.buffer.ByteBuf;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.handler.codec.MessageToByteEncoder;
//import ru.mail.track.Messeges.MessageBase;
//import ru.mail.track.Protocol.Protocol;
//
//
//public class NettyEncode extends MessageToByteEncoder<MessageBase> {
//
//    Protocol protocol;
//
//    public NettyEncode(Protocol protocol) {
//        this.protocol = protocol;
//    }
//
//    @Override
//    protected void encode(ChannelHandlerContext channelHandlerContext, MessageBase messageBase, ByteBuf byteBuf) throws Exception {
//        byte[] data = protocol.encode(messageBase);
//        int dataLength = data.length;
//
//        byteBuf.writeByte((byte) 'F');
//        byteBuf.writeInt(dataLength);
//        byteBuf.writeBytes(data);
//    }
//}
