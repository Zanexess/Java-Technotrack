package ru.mail.track.Data;
import ru.mail.track.Dao.Identified;

public class User implements Identified {
    private Long userId;
    private String nickName;
    private String name;
    private String pass;

    public User(){

    }

    public User(String name, String pass) {
        this.name = name;
        this.pass = pass;
        this.nickName = name;
    }

    public User(String nickName, String name, String pass) {
        this.nickName = nickName;
        this.pass = pass;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setPassword(String password){
        pass = password;
    }

    public String getPassword() {
        return pass;
    }

    public Long getId() {
        return userId;
    }

    public void setId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return new String(userId + " " + nickName + " " + name + " " + pass + "\n");
    }
}
