package ru.mail.track.Session;


import ru.mail.track.Data.User;
import ru.mail.track.Network.ConnectionHandler;

public class Session {
    private User sessionUser;
    private Long id;
    private ConnectionHandler connectionHandler;

    public ConnectionHandler getConnectionHandler() {
        return connectionHandler;
    }

    public void setConnectionHandler(ConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
    }

    public Session() {

    }

    public Session(Long id){
        this.id = id;
    }

    public boolean isUserAuthentificated() {
        if (sessionUser == null)
            return false;
        else
            return true;
    }

    public User getSessionUser() { return sessionUser; }

    public void setSessionUser(User sessionUser) {
        this.sessionUser = sessionUser;
    }

    public Long getId(){
        return id;
    }
}
