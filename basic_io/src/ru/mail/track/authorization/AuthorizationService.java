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
            System.out.println("Hello, " + name);
            return userStore.getUser(name, password);
        } else {
            System.out.println("We can't find User");
            return null;
        }
    }

    public User createUser() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Username: ");
        String name = scanner.next();

        System.out.println("Password");
        String pass = scanner.next();

        if (!userStore.isUserExist(name)) {
            User user = new User(name, pass);
            userStore.addUser(user);
            return user;
        } else {
            System.out.println("User already exist");
            return null;
        }
    }
}
