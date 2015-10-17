package ru.mail.track.session;

public class User {

    private String nickName;
    private String name;
    private String pass;

    public User(String name, String pass) {
        this.name = name;
        this.pass = pass;
        this.nickName = name;
    }
    public User(String nickName, String name, String pass) {
        this.nickName = nickName;
        this.pass = pass;
        this.nickName = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return new String(nickName + " " + name + " " + pass + "\n");
    }
}
