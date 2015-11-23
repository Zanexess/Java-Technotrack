package ru.mail.track.Comands;

import ru.mail.track.Authorization.AuthorizationService;
import ru.mail.track.Session.SessionManager;
import ru.mail.track.Session.Session;
import ru.mail.track.Data.User;

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

