package igushkin.homeworks.lesson11.task3;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Updater extends ChatHandler implements Runnable{

    private static final int FROM = 20;
    private static final int TO = 40;

    public static int countCreations = 0;

    private int period;

    public Updater (Chat chat) {
        super(chat, ++countCreations);
        this.period = generatePeriod(FROM, TO);
    }

    public int getPeriod() {
        return period;
    }

    @Override
    public void run() {
        getChat().lock.lock();
        try {
            log.info("Updater № {} updating message:", getMyNum());
            getChat().updateRandomMessage();
        } finally {
            getChat().lock.unlock();
        }
    }
}
