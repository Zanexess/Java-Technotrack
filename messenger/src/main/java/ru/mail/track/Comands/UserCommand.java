package ru.mail.track.Comands;

import ru.mail.track.Stores.UserStore;
import ru.mail.track.Session.Session;

/**
 * Сменить ник
 */
public class UserCommand implements Command {
    private UserStore userStore;

    public UserCommand(UserStore userStore) {
        this.userStore = userStore;
    }

    public Result execute(Session session, String[] args) {
        if (args.length == 2) {
            if (session.isUserAuthentificated()) {
                session.getSessionUser().setNickName(args[1]);
                userStore.update(session.getSessionUser());
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
