package ru.mail.track.comands;

import ru.mail.track.session.Session;

/**
 * Информация о текущем пользователе
 */
public class SessionInfoCommand implements Command {
    @Override
    public Result execute(Session session, String[] args) {
        try {
            System.out.println(session.getSessionUser().toString());
        } catch (NullPointerException e) {
            System.out.println("User is not log in");
        }
        return new Result(1);
    }
}
