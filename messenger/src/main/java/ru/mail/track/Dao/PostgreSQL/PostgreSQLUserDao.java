package ru.mail.track.Dao.PostgreSQL;

import ru.mail.track.Dao.AbstractJDBCDao;
import ru.mail.track.Dao.Exceptions.PersistException;
import ru.mail.track.Data.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;


public class PostgreSQLUserDao extends AbstractJDBCDao<User> {
    private Connection connection;

    private class PersistUser extends User {
        public void setId(Long id) {
            super.setId(id);
        }
    }

    @Override
    public String getSelectQuery() {
        return "SELECT id, nickname, name, password FROM public.User ";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO public.User (nickname, name, password) \n" +
                "VALUES (?, ?, ?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE public.user \n" +
                "SET nickname = ?, name  = ?, password = ? \n" +
                "WHERE id = ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM public.user WHERE id= ?;";
    }

    public PostgreSQLUserDao(Connection connection) {
        super(connection);
    }

    @Override
    protected List<User> parseResultSet(ResultSet rs) throws PersistException {
        LinkedList<User> result = new LinkedList<User>();
        try {
            while (rs.next()) {
                PersistUser user = new PersistUser();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setNickName(rs.getString("nickname"));
                user.setPassword(rs.getString("password"));
                result.add(user);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, User object) throws PersistException {
        try {
            statement.setString(1, object.getNickName());
            statement.setString(2, object.getName());
            statement.setString(3, object.getPassword());
            statement.setLong(4, object.getId());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, User object) throws PersistException {
        try {
            statement.setString(1, object.getNickName());
            statement.setString(2, object.getName());
            statement.setString(3, object.getPassword());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected String getSequence() {
        return "user_id_seq";
    }

    @Override
    protected String getSeqClass() {
        return "user";
    }
}
