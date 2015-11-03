package ru.mail.track;

import java.util.Scanner;

public class AuthorizationService {

    private UserStore userStore;

    public AuthorizationService(UserStore userStore) {
        this.userStore = userStore;
    }

    void startAuthorization() {
        Scanner scanner = new Scanner(System.in);
        if (!isLogin()) {
            while ((true)) {
                System.out.println("1 - login; 2 - createUser; 3 - quit;");
                int a = scanner.nextInt();
                switch (a) {
                    case 1:
                        login();
                        break;
                    case 2:
                        createUser();
                        break;
                    case 3:
                        return;
                    default:
                        System.out.println("Something Wrong");
                }
            }
        }
    }

    User login() {
//            1. Ask for name
//            2. Ask for password
//            3. Ask UserStore for user:  userStore.getUser(name, pass)
        Scanner scanner = new Scanner(System.in);
        boolean success = false;

        System.out.println("Sign in:");
        while (!success) {
            System.out.println("Username: ");
            String name = scanner.next();
            System.out.println(">" + name);

            System.out.println("Password");
            String pass = scanner.next();
            System.out.println(">" + pass);

            if (userStore.isUserExist(name)) {
                User tmpUser = userStore.getUser(name, pass);
                if (tmpUser != null) {
                    success = true;
                    System.out.println("Hello, " + name);
                    return userStore.getUser(name, pass);
                }
            }
            else
            {
                System.out.println("We can't find User");
                return null;
            }
            System.out.println("Try again");
        }
        return null;
    }

    User createUser() {
        // 1. Ask for name
        // 2. Ask for pass
        // 3. Add user to UserStore: userStore.addUser(user)
        System.out.println("Sign up");
        Scanner scanner = new Scanner(System.in);


        System.out.println("Username: ");
        String name = scanner.next();
        System.out.println(">" + name);

        System.out.println("Password");
        String pass = scanner.next();
        System.out.println(">" + pass);

        if (!userStore.isUserExist(name)) {
            User user = new User(name, pass);
            userStore.addUser(user);
            return user;
        }
        else
        {
            System.out.println("User already exist");
            return null;
        }
    }

    boolean isLogin() {
        return false;
    }
}
