package ru.mail.track.Data;

import ru.mail.track.Dao.Identified;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Chat implements Identified {

    private Long id;
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Храним список идентификаторов
     */
    private List<Long> messageIds = new ArrayList<Long>();
    private List<Long> participantIds = new ArrayList<Long>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getMessageIds() {
        return messageIds;
    }

    public void setMessageIds(List<Long> messageIds) {
        this.messageIds = messageIds;
    }

    public List<Long> getParticipantIds() {
        return participantIds;
    }

    public void setParticipantIds(List<Long> participantIds) {
        this.participantIds = participantIds;
    }

    public void addParticipant(Long id) {
        participantIds.add(id);
    }

    public void addMessage(Long id) {
        messageIds.add(id);
    }

}
