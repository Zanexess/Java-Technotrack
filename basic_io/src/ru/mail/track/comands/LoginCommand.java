package ru.mail.track.comands;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;
import ru.mail.track.authorization.AuthorizationService;
import ru.mail.track.network.SessionManager;
import ru.mail.track.session.Session;
import ru.mail.track.session.User;

/**
 * Авторизация пользователя
 */
public class LoginCommand implements Command {

    private AuthorizationService service;
    private SessionManager sessionManager;

    public LoginCommand(AuthorizationService service, SessionManager sessionManager) {
        this.service = service;
        this.sessionManager = sessionManager;
    }

    public Result execute(Session session, String[] args) {
        if (session.getSessionUser() == null) {
            if (args.length == 3) {
                User user = service.login(args[1], args[2]);
                if (user != null) {
                    session.setSessionUser(user);
                    sessionManager.registerUser(user.getId(), session.getId());
                }
                else return new Result(Result.Status.LoginError, "Login or password are incorrect");
            } else {
                return new Result(Result.Status.InvalidInput,
                        "Format of command: \\login <Username> <Password>");
            }
        } else {
            return new Result(Result.Status.LoginError, "You already log in");
        }
        return new Result(Result.Status.Success, "Hello, " + args[1] +
                ", you're successfully log in");
    }
}
