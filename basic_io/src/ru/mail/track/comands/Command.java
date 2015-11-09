package ru.mail.track.comands;

import ru.mail.track.session.Session;

public interface Command {
    Result execute(Session session, String[] args);
}
