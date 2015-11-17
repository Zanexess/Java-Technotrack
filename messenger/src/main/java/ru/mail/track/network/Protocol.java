package ru.mail.track.network;

import ru.mail.track.Messeges.MessageBase;

public interface Protocol {
    MessageBase decode(byte[] bytes);
    byte[] encode(MessageBase msg);
}