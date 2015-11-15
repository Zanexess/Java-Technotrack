package ru.mail.track.comands;

import ru.mail.track.authorization.AuthorizationService;
import ru.mail.track.network.SessionManager;
import ru.mail.track.session.Session;
import ru.mail.track.session.User;

/**
 * Created by zanexess on 09.11.15.
 */
public class RegistrationCommand implements Command {

    private AuthorizationService service;
    private SessionManager sessionManager;

    public RegistrationCommand(AuthorizationService service, SessionManager sessionManager) {
        this.service = service;
        this.sessionManager = sessionManager;
    }

    public Result execute(Session session, String[] args) {
        if (session.getSessionUser() == null) {
            if (args.length == 3) {
                User user = service.createUser(args[1], args[2]);
                if (user != null)
                    return new Result(Result.Status.Success, "You're successfully sign in");
                else return new Result(Result.Status.LoginError, "User already exists");
            } else {
                return new Result(Result.Status.InvalidInput,
                        "Format of command: \\register <Username> <Password>");
            }
        } else {
            return new Result(Result.Status.LoginError, "You already log in");
        }
    }
}

