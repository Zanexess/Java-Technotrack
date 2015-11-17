package ru.mail.track.authorization;

import ru.mail.track.session.User;

public interface  UserStore {

    boolean isUserExist(String name);

    void addUser(User user);

    User getUser(String name, String pass);

    User getUser(Long id);
}
