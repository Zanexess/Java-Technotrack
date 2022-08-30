package ru.mail.track.comands;

import com.sun.xml.internal.bind.v2.TODO;
import ru.mail.track.authorization.UserStore;
import ru.mail.track.session.Session;
import ru.mail.track.session.User;

import javax.jws.soap.SOAPBinding;

/**
 * Информация о текущем пользователе
 */
public class SessionInfoCommand implements Command {
    private UserStore userStore;
    private User user;

    public SessionInfoCommand(UserStore userStore) {
        this.userStore = userStore;
    }

    public Result execute(Session session, String[] args) {
        if (session.isUserAuthentificated())
            if (args.length == 1) {
                return new Result(Result.Status.Success,
                        session.getSessionUser().toString());
            } else if (args.length == 2) {
                try {
                    long id = Long.parseLong(args[1]);
                    user = userStore.getUser(id);
                    if (user != null) {
                        return new Result(Result.Status.Success, user.toString());
                    } else {
                        return new Result(Result.Status.InvalidInput,
                                "User <" + id + "> doesn't exist");
                    }
                } catch (NumberFormatException e) {
                    return new Result(Result.Status.InvalidInput,
                            "Format of command: \\info or \\info <int id>");
                }
            } else {
                return new Result(Result.Status.InvalidInput,
                        "Format of command: \\info or \\info <int id>");
            }
        else {
            return new Result(Result.Status.LoginError, "You are not log in");
        }
    }
}
