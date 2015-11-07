package ru.mail.track.comands;

import ru.mail.track.session.Session;

/**
 * Сменить ник
 */
public class UserCommand implements Command {
    @Override
    public Result execute(Session session, String[] args) {
        if (args.length == 2) {
            if (session.getSessionUser() != null) {
                session.getSessionUser().setNickName(args[1]);
            }
        } else {
            return new Result(Result.Status.InvalidInput);
        }
        return new Result(Result.Status.Success);
    }

}
