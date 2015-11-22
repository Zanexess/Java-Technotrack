package ru.mail.track.PostgreSQL;

import ru.mail.track.ConnectionPool.JDBCConnectionPool;
import ru.mail.track.Dao.DaoFactory;
import ru.mail.track.Dao.Exceptions.PersistException;
import ru.mail.track.Dao.GenericDao;
import ru.mail.track.data.Chat;
import ru.mail.track.data.Message;
import ru.mail.track.session.User;
import ru.mail.track.session.UserChatRelation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class PostgreSQLDaoFactory implements DaoFactory<Connection> {

    private final String user = "senthil";
    private final String password = "ubuntu";
    private final String url = "jdbc:postgresql://178.62.140.149:5432/Zanexess";
    private final String driver = "org.postgresql.Driver";

    private Map<Class, DaoCreator> creators;

    public Connection getContext() throws PersistException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("_____");
        } catch (SQLException e) {
            throw new PersistException(e);
        }
        return  connection;

//        // Create the ConnectionPool:
//        JDBCConnectionPool pool = new JDBCConnectionPool(driver, url, user, password);
//        // Get a connection:
//        Connection con = pool.checkOut();
//        // Return the connection:
//        pool.checkIn(con);
//        return con;
    }

    @Override
    public GenericDao getDao(Connection connection, Class dtoClass) throws PersistException {
        DaoCreator creator = creators.get(dtoClass);
        if (creator == null) {
            throw new PersistException("Dao object for " + dtoClass + " not found.");
        }
        return creator.create(connection);
    }

    public PostgreSQLDaoFactory() {
        try {
            Class.forName(driver);//Регистрируем драйвер
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        creators = new HashMap<Class, DaoCreator>();
        creators.put(User.class, new DaoCreator<Connection>() {
            @Override
            public GenericDao create(Connection connection) {
                return new PostgreSQLUserDao(connection);
            }
        });
        creators.put(Message.class, new DaoCreator<Connection>() {
            @Override
            public GenericDao create(Connection connection) {
                return new PostgreSQLMessageDao(connection);
            }
        });
        creators.put(Chat.class, new DaoCreator<Connection>() {
            @Override
            public GenericDao create(Connection connection) {
                return new PostgreSQLChatDao(connection);
            }
        });
        creators.put(UserChatRelation.class, new DaoCreator<Connection>() {
            @Override
            public GenericDao create(Connection connection) {
                return new PostgreSQLUserChatDao(connection);
            }
        });
    }
}
