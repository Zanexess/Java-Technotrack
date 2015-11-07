package ru.mail.track.network;

import ru.mail.track.data.Message;
public class Protocol {
    public static Message decode(byte[] bytes) {
        String data = new String(bytes);
        return new Message(data);
    }

    public static byte[] encode(Message msg) {
        return msg.getMessage().getBytes();
    }
}