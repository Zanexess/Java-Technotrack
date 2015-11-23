package ru.mail.track.Authorization;

import ru.mail.track.Stores.UserStore;
import ru.mail.track.Data.User;

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
