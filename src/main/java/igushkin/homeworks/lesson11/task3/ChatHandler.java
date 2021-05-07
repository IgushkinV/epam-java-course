package igushkin.homeworks.lesson11.task3;

import java.util.Random;

public abstract class ChatHandler {

    private int myNum;
    private Chat chat;

    public ChatHandler(Chat chat, int myNum) {
        this.chat = chat;
        this.myNum = myNum;
    }

    public int getMyNum() {
        return myNum;
    }

    public Chat getChat() {
        return chat;
    }

    /**
     * Generates number between from and to.
     *
     * @return int number between [from; to).
     */
    public static int generatePeriod(int from, int to) {
        Random random = new Random();
        return random.nextInt(to - from) + from;
    }
}
