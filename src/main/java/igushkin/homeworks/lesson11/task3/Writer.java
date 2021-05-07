package igushkin.homeworks.lesson11.task3;

import lombok.extern.slf4j.Slf4j;

/**
 * Implements writing messages to chat storage.
 */
@Slf4j
public class Writer extends ChatHandler implements Runnable {
    public static int numberOfWriteOperation = 0;
    public static int countCreations = 0;

    private static final int FROM = 20;
    private static final int TO = 60;

    private int period;

    public Writer(Chat chat) {
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
            var message = getMyNum() + "-Writer, writing operation № " + ++numberOfWriteOperation;
            getChat().writeMessage(message);
            log.info("run() - Попытка записи сообщения - {}", message);
        } finally {
            getChat().lock.unlock();
        }
    }
}
