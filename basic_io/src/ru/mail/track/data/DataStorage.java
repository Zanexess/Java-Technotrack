package ru.mail.track.data;

import ru.mail.track.session.Session;

import java.util.List;

public interface DataStorage {
    void addMessage(String str);
    List<String> getHistory(int n) throws NullPointerException;
    void printHistory(List<String> history);
}
