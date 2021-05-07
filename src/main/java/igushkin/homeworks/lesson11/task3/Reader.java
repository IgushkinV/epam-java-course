package igushkin.homeworks.lesson11.task3;

import lombok.extern.slf4j.Slf4j;

/**
 * Implements reading messages from the chat storage. Delete message after reading.
 */
@Slf4j
public class Reader extends ChatHandler implements Runnable{

    private static final int FROM = 30;
    private static final int TO = 50;

    public static int countCreations = 0;

    private int period;

    public Reader (Chat chat) {
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
            var message = getChat().readMessage();
            log.info("run() - \"{}\" - was red by Reader № {}", message, getMyNum());
        } finally {
            getChat().lock.unlock();
        }
    }
}
