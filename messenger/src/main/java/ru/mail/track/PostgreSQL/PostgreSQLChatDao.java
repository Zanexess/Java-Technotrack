package ru.mail.track.PostgreSQL;

import ru.mail.track.Dao.AbstractJDBCDao;
import ru.mail.track.Dao.Exceptions.PersistException;
import ru.mail.track.data.Chat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;


public class PostgreSQLChatDao extends AbstractJDBCDao<Chat> {
    private Connection connection;

    private class PersistChat extends Chat {
        public void setId(Long id) {
            super.setId(id);
        }
    }

    @Override
    public String getSelectQuery() {
        return "SELECT id, title FROM public.chat ";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO public.chat (title) \n" +
                "VALUES (?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE public.chat \n" +
                "SET title = ? \n" +
                "WHERE id = ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM public.chat WHERE id= ?;";
    }


    public PostgreSQLChatDao(Connection connection) {
        super(connection);
    }

    @Override
    protected List<Chat> parseResultSet(ResultSet rs) throws PersistException {
        LinkedList<Chat> result = new LinkedList<Chat>();
        try {
            while (rs.next()) {
                PersistChat chat = new PersistChat();
                chat.setId(rs.getLong("id"));
                chat.setTitle(rs.getString("title"));
                result.add(chat);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Chat object) throws PersistException {
        try {
            statement.setString(1, object.getTitle());
            statement.setLong(2, object.getId());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Chat object) throws PersistException {
        try {
            statement.setString(1, object.getTitle());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected String getSequence() {
        return "chat_id_seq";
    }

    @Override
    protected String getSeqClass() {
        return "chat";
    }
}
