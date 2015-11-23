package ru.mail.track.Protocol;

import ru.mail.track.Messeges.MessageBase;

public interface Protocol {
    MessageBase decode(byte[] bytes);
    byte[] encode(MessageBase msg);
}