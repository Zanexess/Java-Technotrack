//package ru.mail.track.Network.NettyWorking.Client;
//
//
//import io.netty.bootstrap.Bootstrap;
//import io.netty.channel.Channel;
//import io.netty.channel.EventLoopGroup;
//import io.netty.channel.nio.NioEventLoopGroup;
//import io.netty.channel.socket.nio.NioSocketChannel;
//import ru.mail.track.Messeges.MessageBase;
//import ru.mail.track.Messeges.MessageType;
//import ru.mail.track.Messeges.Request;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//
//public class NettyClient {
//    private String host;
//    private int port;
//
//    public static void main(String[] args) throws Exception {
//        new NettyClient("localhost", 19000).runServer();
//    }
//
//    public NettyClient(String host, int port) {
//        this.host = host;
//        this.port = port;
//    }
//
//    public void runServer() throws Exception {
//        EventLoopGroup group = new NioEventLoopGroup();
//
//        try {
//            Bootstrap bootstrap = new Bootstrap()
//                    .group(group)
//                    .channel(NioSocketChannel.class)
//                    .handler(new ClientInitializer());
//            Channel channel = bootstrap.connect(host, port).sync().channel();
//            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//
//            while (true) {
//                String line = in.readLine();
//                MessageBase msg = processInput(line);
//                if (msg != null)
//                    channel.write(msg);
//            }
//        } finally {
//            //TODO shutdownRead
//            group.shutdownGracefully();
//        }
//    }
//
//    public MessageBase processInput(String line) throws IOException {
//        MessageBase msg = null;
//        String args[] = line.split(" ");
//        if (args[0].startsWith("\\")) {
//            Request request = Request.getType(args[0]);
//            switch (request) {
//                case REQUEST_EXIT:
//                    msg = new MessageBase(MessageType.MSG_LOGOUT, args);
//                    break;
//                case REQUEST_LOGIN:
//                    msg = new MessageBase(MessageType.MSG_LOGIN, args);
//                    break;
//                case REQUEST_REGISTER:
//                    msg = new MessageBase(MessageType.MSG_REGISTER, args);
//                    break;
//                case REQUEST_INFO:
//                    msg = new MessageBase(MessageType.MSG_INFO, args);
//                    break;
//                case REQUEST_HELP:
//                    msg = new MessageBase(MessageType.MSG_HELP, args);
//                    break;
//                case REQUEST_USER:
//                    msg = new MessageBase(MessageType.MSG_USER, args);
//                    break;
//                case REQUEST_USERPASS:
//                    msg = new MessageBase(MessageType.MSG_USERPASS, args);
//                    break;
//                case REQUEST_CHATLIST:
//                    msg = new MessageBase(MessageType.MSG_CHATLIST, args);
//                    break;
//                case REQUEST_CHATCREATE:
//                    msg = new MessageBase(MessageType.MSG_CHATCREATE, args);
//                    break;
//                case REQUEST_CHATSEND:
//                    msg = new MessageBase(MessageType.MSG_CHATSEND, args);
//                    break;
//                case REQUEST_CHATHISTORY:
//                    msg = new MessageBase(MessageType.MSG_CHATHISTORY, args);
//                    break;
//                case REQUEST_CHATFIND:
//                    msg = new MessageBase(MessageType.MSG_CHATFIND, args);
//                    break;
//                default:
//                    System.out.println("INVALID_INPUT");
//                    break;
//            }
//        } else {
//            System.out.println("INVALID_INPUT");
//        }
//        return msg;
//    }
//}
