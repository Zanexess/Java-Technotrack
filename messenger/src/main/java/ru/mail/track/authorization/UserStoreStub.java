package ru.mail.track.authorization;
import ru.mail.track.session.User;

import java.util.HashMap;
import java.util.Map;


public class UserStoreStub implements UserStore {

    private static Map<Long, User> users = new HashMap<Long, User>();

    static {
        User u0 = new User("A", "1");
        u0.setId(0L);

        User u1 = new User("B", "1");
        u1.setId(1L);

        User u2 = new User("C", "1");
        u2.setId(2L);

        User u3 = new User("D", "1");
        u3.setId(3L);

        users.put(0L, u0);
        users.put(1L, u1);
        users.put(2L, u2);
        users.put(3L, u3);
    }

    public void addUser(User user) {
        users.put(user.getId(), user);
    }

    public User getUser(String login, String pass) {
        for (User user : users.values()) {
            if (user.getName().equals(login) && user.getPassword().equals(pass)) {
                return user;
            }
        }
        return null;
    }

    public User getUserById(Long id) {
        return null;
    }

    public User getUser(Long id) {
        return users.get(id);
    }

    public boolean isUserExist(String name) {
        for (long i = 0; i < users.size(); i++) {
            if (users.get(i).getName().equals(name))
                return true;
        }
        return false;
    }

    @Override
    public void update(User user) {}
}
