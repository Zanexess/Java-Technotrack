package ru.mail.track.data;

import ru.mail.track.session.Session;

import java.util.List;

public interface DataStorage {
    void addMessage(Message message);
    List<Message> getHistory(int n);
}
