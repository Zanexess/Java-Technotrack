package ru.mail.track.Network.NioRox;

import java.nio.channels.SocketChannel;

/**
 * Created by zanexess on 01.12.15.
 */
public class ChangeRequest {
    public static final int REGISTER = 1;
    public static final int CHANGEOPS = 2;

    public SocketChannel socket;
    public int type;
    public int ops;

    public ChangeRequest(SocketChannel socket, int type, int ops){
        this.socket = socket;
        this.type = type;
        this.ops = ops;
    }
}
