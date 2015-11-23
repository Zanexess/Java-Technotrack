package ru.mail.track.Comands;

import ru.mail.track.Stores.UserStore;
import ru.mail.track.Session.Session;

public class UserPassCommand implements Command {
    private UserStore userStore;

    public UserPassCommand(UserStore userStore){
        this.userStore = userStore;
    }
    public Result execute(Session session, String[] args) {
        if (args.length == 3) {
            if (session.isUserAuthentificated()) {
                if (args[1].equals(session.getSessionUser().getPassword())) {
                    session.getSessionUser().setPassword(args[2]);
                    userStore.update(session.getSessionUser());
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
