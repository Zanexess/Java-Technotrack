package ru.mail.track.authorization;

import ru.mail.track.session.User;

import java.util.Scanner;

public class AuthorizationService {

    private UserStore userStore;
    public AuthorizationService(UserStore userStore) {
        this.userStore = userStore;
    }

    public User login(String name, String password) {
        if (userStore.isUserExist(name) && userStore.getUser(name, password) != null) {
            return userStore.getUser(name, password);
        } else return null;
    }

    public User createUser(String name, String password) {
        if (!userStore.isUserExist(name)) {
            User user = new User(name, password);
            userStore.addUser(user);
            return user;
        } else {
            return null;
        }
    }
}
