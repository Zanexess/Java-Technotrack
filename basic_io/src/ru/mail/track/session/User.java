package ru.mail.track.session;

import java.util.concurrent.atomic.AtomicLong;

public class User {
    private AtomicLong userId = new AtomicLong(0);
    private String nickName;
    private String name;
    private String pass;

    public User(String name, String pass) {
        this.name = name;
        this.pass = pass;
        this.nickName = name;
        userId.incrementAndGet();
    }

    public User(String nickName, String name, String pass) {
        this.nickName = nickName;
        this.pass = pass;
        this.name = name;
        userId.incrementAndGet();
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

    public void setPassword(String password){
        pass = password;
    }

    public long getId(){
        return userId.get();
    }

    public void setId(Long id){
        this.userId = new AtomicLong(id);
    }

    @Override
    public String toString() {
        return new String(userId + " " + nickName + " " + name + " " + pass + "\n");
    }
}
