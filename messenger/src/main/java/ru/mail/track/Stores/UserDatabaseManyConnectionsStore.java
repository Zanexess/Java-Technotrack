package ru.mail.track.Stores;

import com.sun.jndi.toolkit.dir.ContextEnumerator;
import ru.mail.track.Dao.DaoFactory;
import ru.mail.track.Dao.Exceptions.PersistException;
import ru.mail.track.Dao.GenericDao;
import ru.mail.track.Data.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserDatabaseManyConnectionsStore implements UserStore{
    private DaoFactory daoFactory;

    public UserDatabaseManyConnectionsStore(DaoFactory daoFactory) {
            this.daoFactory = daoFactory;
    }

    @Override
    public boolean isUserExist(String name) {
        List<User> list;
        try {
            Connection connection = (Connection)daoFactory.getContext();
            GenericDao userDao = daoFactory.getDao(connection, User.class);
            list = userDao.getAll();
            for (User user : list) {
                if (user.getName().equals(name)){
                    connection.close();
                    return true;
                }
            }
            connection.close();
        } catch (PersistException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void addUser(User user) {
        try {
            Connection connection = (Connection)daoFactory.getContext();
            GenericDao userDao = daoFactory.getDao(connection, User.class);
            userDao.persist(user);
            connection.close();
        } catch (PersistException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User getUser(String name, String pass) {
        List<User> list;
        try {
            Connection connection = (Connection)daoFactory.getContext();
            GenericDao userDao = daoFactory.getDao(connection, User.class);
            list = userDao.getAll();
            for (User user : list) {
                if (user.getName().equals(name) && user.getPassword().equals(pass)) {
                    connection.close();
                    return user;
                }
            }
            connection.close();
        } catch (PersistException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User getUser(Long id) {
        try {
            Connection connection = (Connection)daoFactory.getContext();
            GenericDao userDao = daoFactory.getDao(connection, User.class);
            User user = (User)userDao.getByPK(id);
            connection.close();
            return user;
        } catch (PersistException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(User user) {
        try {
            Connection connection = (Connection)daoFactory.getContext();
            GenericDao userDao = daoFactory.getDao(connection, User.class);
            userDao.update(user);
            connection.close();
        } catch (PersistException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isUserExist(Long id) {
        List<User> list;
        try {
            Connection connection = (Connection)daoFactory.getContext();
            GenericDao userDao = daoFactory.getDao(connection, User.class);
            list = userDao.getAll();
            for (User user : list) {
                if (user.getId() == id){
                    connection.close();
                    return true;
                }
            }
            connection.close();
        } catch (PersistException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
