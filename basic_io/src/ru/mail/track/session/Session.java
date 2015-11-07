package ru.mail.track.session;


public class Session {

    private User sessionUser;

    public Session() {

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
}
