package ru.mail.track.comands;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;
import ru.mail.track.authorization.AuthorizationService;
import ru.mail.track.session.Session;

/**
 * Авторизация пользователя
 */
public class LoginCommand implements Command {

    private AuthorizationService service;

    public LoginCommand(AuthorizationService service) {
        this.service = service;
    }

    @Override
    public Result execute(Session session, String[] args) {
        if (session.getSessionUser() == null) {
            if (args.length == 3) {
                session.setSessionUser(service.login(args[1], args[2]));
            } else if (args.length == 1) {
                service.createUser();
            } else {
                return new Result(Result.Status.InvalidInput);
            }
        } else {
            System.out.println("User already log in.");
            return new Result(Result.Status.LoginError);
        }
        return new Result(Result.Status.Success);
    }
}
