package ru.mail.track.comands;

import ru.mail.track.session.Session;

/**
 * Выйти из профиля
 */
public class ExitCommand implements Command {
    public Result execute(Session session, String[] args) {
        if (session.getSessionUser() != null) {
            session.setSessionUser(null);
            return new Result(Result.Status.Success, "You successfully log out");
        } else {
            return new Result(Result.Status.LoginError, "User is not log in");
        }
    }
}
