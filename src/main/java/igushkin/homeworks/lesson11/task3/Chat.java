package igushkin.homeworks.lesson11.task3;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Provides "Chat" storage to store messages and methods to write, read and update messages.
 */
@Slf4j
public class Chat {

    public static final int MAX_CAPACITY = 25;
    public static final int WRITERS_COUNT = 10;
    public static final int READERS_COUNT = 5;
    public static final int UPDATERS_COUNT = 5;

    private ConcurrentHashMap<Integer, String> messages = new ConcurrentHashMap<>();

    Lock lock = new ReentrantLock();

    ScheduledExecutorService writers = Executors.newScheduledThreadPool(WRITERS_COUNT);
    ScheduledExecutorService readers = Executors.newScheduledThreadPool(READERS_COUNT);
    ScheduledExecutorService updaters = Executors.newScheduledThreadPool(UPDATERS_COUNT);

    /**
     * Starts and schedules pull of Writers.
     */
    public void startWriters() {
        List<Writer> writerList = createWriters(WRITERS_COUNT);
        log.info("startWriters() - Передано {} писателей", writerList.size());
        writerList.forEach(writer -> {
            writers.scheduleAtFixedRate(writer, 0, writer.getPeriod(), TimeUnit.SECONDS);
            log.info("startWriters() - Запускаем Writer {} с периодом {} с", writer.getMyNum(), writer.getPeriod());
        });
    }

    /**
     * Starts and schedules pull of Readers.
     */
    public void startReaders() {
        List<Reader> readerList = createReaders(READERS_COUNT);
        log.info("startReaders() - Передано {} читателей", readerList.size());
        readerList.forEach(reader -> {
            readers.scheduleAtFixedRate(reader, 0, reader.getPeriod(), TimeUnit.SECONDS);
            log.info("startReaders() - Запускаем Reader {} с периодом {} с", reader.getMyNum(), reader.getPeriod());
        });
    }


    /**
     * Starts and schedules pull of Updaters.
     */
    public void startUpdaters() {
        List<Updater> updaterList = createUpdaters(UPDATERS_COUNT);
        log.info("startUpdaters() - Передано {} апдейтеров.", updaterList.size());
        updaterList.forEach(updater -> {
            updaters.scheduleAtFixedRate(updater, 5, updater.getPeriod(), TimeUnit.SECONDS);
            log.info("startUpdaters() - Запускаем Updater {} с периодом {} с", updater.getMyNum(), updater.getPeriod());
        });
    }

    /**
     * Creates Writers and add it to list.
     *
     * @param numOfWriters num of Writers to create.
     * @return List<Writer>
     */
    public List<Writer> createWriters(int numOfWriters) {
        List<Writer> writers = new ArrayList<>();
        for (var i = 0; i < numOfWriters; i++) {
            writers.add(new Writer(this));
        }
        log.info("createWriters() - Создано {} писателей", writers.size());
        return writers;
    }

    /**
     * Creates Readers and add it to list.
     * @param numOfReaders number of Readers to create.
     * @return List<Reader>
     */
    public List<Reader> createReaders(int numOfReaders) {
        List<Reader> readers = new ArrayList<>();
        for (var i = 0; i < numOfReaders; i++) {
            readers.add(new Reader(this));
        }
        log.info("createReaders() - Создано {} читателей", readers.size());
        return readers;
    }

    /**
     * Creates Updaters and add it to list.
     * @param numOfUpdaters number of Updaters to create.
     * @return List<Updater>
     */
    public List<Updater> createUpdaters(int numOfUpdaters) {
        List<Updater> updaters = new ArrayList<>();
        for (var i = 0; i < numOfUpdaters; i++) {
            updaters.add(new Updater(this));
        }
        log.info("createUpdaters() - Создано {} апдейтеров.", updaters.size());
        return updaters;
    }

    /**
     * Writes message to Chat storage.
     * Checks if chat storage is full and not writes message to chat if it's full.
     * @param message message to write to chat.
     */
    public void writeMessage(String message) {
        if (messages.size() == MAX_CAPACITY) {
            log.info("writeMessage() - Достигнут предел вместительности чата.");
            return;
        }
        messages.put(messages.size(), message);
        log.info("writeMessage() - Message \"{}\" was added", message);
        log.info("writeMessage() - Size of chat is {}", messages.size());
    }

    /**
     * Reads the newest message from the chat. Deletes reared message.
     * @return read message.
     */
    public String readMessage() {
        if (messages.size() <= 0) {
            return "readMessage() - Нет сообщений";
        }
        var message = messages.get(messages.size() - 1);
        log.info("readMessage() - Size of chat before reading {}", messages.size());
        messages.remove(messages.size() - 1);
        log.info("readMessage() - Size of chat after reading {}", messages.size());
        return message;
    }

    /**
     * Updates random message in chat.
     */
    public void updateRandomMessage() {
        Random random = new Random();
        var index = random.nextInt(messages.size());
        log.info("updateRandomMessage() - The message before: {}", messages.get(index));
        messages.compute(index, (key, value) ->
                value.concat("-" + random.nextInt(100)));
        log.info("updateRandomMessage() - The message after: {}", messages.get(index));
    }
}
