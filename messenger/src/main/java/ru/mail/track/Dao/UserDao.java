package ru.mail.track.Dao;

import ru.mail.track.session.User;

import java.sql.SQLException;
import java.util.List;


public interface UserDao {
    public User create() throws SQLException;

    public User read(int key) throws SQLException;

    public void update(User user) throws SQLException;

    public void delete(User user) throws SQLException;

    public List<User> getAll() throws SQLException;
}
