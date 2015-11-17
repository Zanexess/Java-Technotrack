package ru.mail.track.data;

import java.util.List;

public interface DataStorage {
    void addMessage(Message message);
    List<Message> getHistory(int n);
}
