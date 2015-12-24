package ru.mail.track.IntegrationalTests;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.pool.AbstractChannelPoolMap;
import io.netty.channel.pool.ChannelPoolMap;
import io.netty.channel.pool.SimpleChannelPool;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.mail.track.Authorization.AuthorizationService;
import ru.mail.track.Comands.*;
import ru.mail.track.Dao.DaoFactory;
import ru.mail.track.Dao.PostgreSQL.PostgreSQLDaoFactory;
import ru.mail.track.Messeges.MessageBase;
import ru.mail.track.Messeges.MessageType;
import ru.mail.track.Messeges.Request;
import ru.mail.track.Network.*;
import ru.mail.track.Network.NettyTest.NettyWorking.Client.NettyClient;
import ru.mail.track.Network.NettyTest.NettyWorking.Client.SimpleChannelPoolHandler;
import ru.mail.track.Network.NettyTest.NettyWorking.Server.NettyServer;
import ru.mail.track.Protocol.MyProtocol;
import ru.mail.track.Protocol.Protocol;
import ru.mail.track.Session.Session;
import ru.mail.track.Session.SessionManager;
import ru.mail.track.Stores.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class LoginIT implements MessageListener {

    private ThreadClient client;

    //TODO запуск сервера и клиента
    @Before
    public void setup() throws Exception {

    }


    @Override
    public void onMessage(Session session, MessageBase msg) {

    }

    //TODO Авторизация
    @Test
    public void login() {

    }

    //TODO Освободить ресурсы
    @After
    public void deprecate() throws Exception {

    }
}
