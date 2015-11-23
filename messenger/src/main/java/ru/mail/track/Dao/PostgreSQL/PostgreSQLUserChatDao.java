package ru.mail.track.Dao.PostgreSQL;

import ru.mail.track.Dao.AbstractJDBCDao;
import ru.mail.track.Dao.Exceptions.PersistException;
import ru.mail.track.Data.UserChatRelation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class PostgreSQLUserChatDao extends AbstractJDBCDao<UserChatRelation> {

    private class PersistUserChatRelation extends UserChatRelation {
        public void setId(Long id) {
            super.setId(id);
        }
    }

    @Override
    public String getSelectQuery() {
        return "SELECT id, idchat, iduser FROM public.userchat ";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO public.userchat (idchat, iduser) \n" +
                "VALUES (?, ?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE public.userchat \n" +
                "SET idchat = ?, iduser  = ? \n" +
                "WHERE id = ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM public.userchat WHERE id= ?;";
    }

    @Override
    protected List<UserChatRelation> parseResultSet(ResultSet rs) throws PersistException {
        LinkedList<UserChatRelation> result = new LinkedList<UserChatRelation>();
        try {
            while (rs.next()) {
                PersistUserChatRelation userChatRelation = new PersistUserChatRelation();
                userChatRelation.setId(rs.getLong("id"));
                userChatRelation.setChatId(rs.getLong("idchat"));
                userChatRelation.setUserId(rs.getLong("iduser"));
                result.add(userChatRelation);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, UserChatRelation object) throws PersistException {
        try {
            statement.setLong(1, object.getChatId());
            statement.setLong(2, object.getUserId());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, UserChatRelation object) throws PersistException {
        try {
            statement.setLong(1, object.getChatId());
            statement.setLong(2, object.getUserId());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    public PostgreSQLUserChatDao(Connection connection) {
        super(connection);
    }

    @Override
    protected String getSequence() {
        return "userchat_id_seq";
    }

    @Override
    protected String getSeqClass() {
        return "userchat";
    }
}
