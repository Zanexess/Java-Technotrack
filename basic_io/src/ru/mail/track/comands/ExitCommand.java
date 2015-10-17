package ru.mail.track.comands;

import ru.mail.track.session.Session;

/**
 * Выйти из профиля
 */
public class ExitCommand implements Command {
    @Override
    public Result execute(Session session, String[] args) {
        if (session.getSessionUser() != null) {
            System.out.println("You successfully log out");
            session.setSessionUser(null);
            return new Result(1);
        } else {
            System.out.println("User is not log in");
            return new Result(1);
        }


    }
}
