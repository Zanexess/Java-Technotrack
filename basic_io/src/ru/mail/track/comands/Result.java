package ru.mail.track.comands;

/**
 * В дальнейшем сделать логи на базе этого с различными кодами ошибок
 */
public class Result {
    enum Status {
        Success,
        Error,
        InvalidInput,
        LoginError,
        SendSuccess,
    }
    private Status status;
    private String[] info;

    Result(Status status){
        this.status = status;
    }

    Result(Status status, String info){
        this.status = status;
        this.info = new String[1];
        this.info[0] = info;
    }

    Result(Status status, String[] info){
        this.status = status;
        this.info = info;
    }

    public void setStatus(Status status){
        this.status = status;
    }

    public void setInfo(String info){
        this.info[0] = info;
    }

    public String[] getInfo(){
        return info;
    }

    public Status getStatus(){
        return status;
    }
}
