//package ru.mail.track.Network.NettyWorking.Server;
//
//import io.netty.buffer.ByteBuf;
//import io.netty.buffer.MessageBuf;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.handler.codec.ByteToMessageDecoder;
//import io.netty.handler.codec.CorruptedFrameException;
//import ru.mail.track.Protocol.Protocol;
//
//
//public class NettyDecode extends ByteToMessageDecoder {
//    private Protocol protocol;
//
//    public NettyDecode(Protocol protocol) {
//        this.protocol = protocol;
//    }
//
//
//    @Override
//    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, MessageBuf<Object> messageBuf) throws Exception {
//        if (byteBuf.readableBytes() < 5) {
//            return;
//        }
//
//        byteBuf.markReaderIndex();
//
//        int magicNumber = byteBuf.readUnsignedByte();
//        if (magicNumber != 'F') {
//            byteBuf.resetReaderIndex();
//            throw new CorruptedFrameException("Invalid magic number");
//            }
//
//        int dataLength = byteBuf.readInt();
//        if (byteBuf.readableBytes() < dataLength) {
//            byteBuf.resetReaderIndex();
//            return;
//            }
//
//        byte[] decoded = new byte[dataLength];
//        byteBuf.readBytes(decoded);
//
//        messageBuf.add(protocol.decode(decoded));
//    }
//}
