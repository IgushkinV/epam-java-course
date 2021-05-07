package igushkin.homeworks.lesson11.task3;

public class Message {

    //todo: подумать, нужно ли поле author
    private String message;

    public Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
