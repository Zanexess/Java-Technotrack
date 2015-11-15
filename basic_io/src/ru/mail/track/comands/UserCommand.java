package ru.mail.track.comands;

import ru.mail.track.session.Session;

/**
 * Сменить ник
 */
public class UserCommand implements Command {
    public Result execute(Session session, String[] args) {
        if (args.length == 2) {
            if (session.isUserAuthentificated()) {
                session.getSessionUser().setNickName(args[1]);
            } else {
                return new Result(Result.Status.LoginError, "You are not log in");
            }
        } else {
            return new Result(Result.Status.InvalidInput,
                    "Format of command: \\user <Nickname>");
        }
        return new Result(Result.Status.Success, "Nickname changed to " + args[1]);
    }
}
