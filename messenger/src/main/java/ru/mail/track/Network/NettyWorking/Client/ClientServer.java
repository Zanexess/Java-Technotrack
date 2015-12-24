//package ru.mail.track.Network.NettyWorking.Client;
//
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.ChannelInboundMessageHandlerAdapter;
//import ru.mail.track.Messeges.MessageBase;
//
//public class ClientHandler extends ChannelInboundMessageHandlerAdapter<MessageBase> {
//    @Override
//    public void messageReceived(ChannelHandlerContext channelHandlerContext, MessageBase message) throws Exception {
//        //System.out.println(message.toString());
//        parseResult(message);
//    }
//
//    public void parseResult(MessageBase msg) {
//        switch (msg.getMessageType()) {
//            case SRV_ERROR:
//                System.out.println("ERROR: " + msg.getArgs()[0]);
//                break;
//            case SRV_SUCCESS:
//                System.out.println("SUCCESS: ");
//                for (int i = 0; i < msg.getArgs().length; i++) {
//                    System.out.print(msg.getArgs()[i] + " ");
//                    System.out.println();
//                }
//                break;
//            case SRV_LOGINERROR:
//                System.out.println("LOGIN ERROR: " + msg.getArgs()[0]);
//                break;
//            case SRV_INVALIDINPUT:
//                System.out.println("INVALID INPUT: " + msg.getArgs()[0]);
//                break;
//            case SRV_NEWMESSAGE:
//                System.out.println("NEWMESSAGE: ");
//                for (int i = 0; i < msg.getArgs().length; i++) {
//                    System.out.print(msg.getArgs()[i] + " ");
//                    System.out.println();
//                }
//                break;
//        }
//    }
//}
