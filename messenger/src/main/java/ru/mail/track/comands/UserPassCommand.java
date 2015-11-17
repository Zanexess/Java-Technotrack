package ru.mail.track.comands;

import ru.mail.track.session.Session;

public class UserPassCommand implements Command {
    public Result execute(Session session, String[] args) {
        if (args.length == 3) {
            if (session.isUserAuthentificated()) {
                if (args[1].equals(session.getSessionUser().getPass())) {
                    session.getSessionUser().setPassword(args[2]);
                } else {
                    return new Result(Result.Status.InvalidInput, "Incorrect old password");
                }
            } else {
                return new Result(Result.Status.LoginError, "You are not log in");
            }
        } else {
            return new Result(Result.Status.InvalidInput,
                    "Format of command: \\user_pass <old password> <new password>");
        }
        return new Result(Result.Status.Success, "Password changed to " + args[2]);
    }
}
