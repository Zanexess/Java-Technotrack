package ru.mail.track.Comands;

import ru.mail.track.Session.Session;

public interface Command {
    Result execute(Session session, String[] args);
}
