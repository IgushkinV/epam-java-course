package igushkin.homeworks;

import igushkin.homeworks.lesson10.task2.Sausage;
import igushkin.homeworks.lesson10.task2.TaskUtilities;
import igushkin.homeworks.lesson10.task3.FileSystemObject;
import igushkin.homeworks.lesson10.taskone.TaskOne;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Demonstrates solution of Task1 and Task2 of lesson 10 homework.
 */
@Slf4j
public class Main {

    public final static Path PATH = Path.of("src/main/resources/file.txt");
    public final static Path SAUSAGES_THERE = Path.of("src/main/resources/sausage.txt");
    public final static Path EMPTY_FILE = Path.of("src/main/resources/null.txt");

    public static void main(String[] args) {
        demonstrateTaskOne();
        demonstrateTaskTwo();
        demonstrateTaskThree();
    }

    //пока только составляет объектное представление, вывод еще не реализован.
    public static void demonstrateTaskThree() {
        FileSystemObject fileSystemObject = new FileSystemObject(Path.of("src/"));
        FileSystemObject.recurse(fileSystemObject);
        log.debug("main() - {}", fileSystemObject.getChildren().toString());
    }

    public static boolean demonstrateTaskTwo() {
        boolean success = true;
        log.info("*************** Task 2 demonstration (with 1 star) ***************");

        Sausage sausage1 = new Sausage(); //type = null
        Sausage sausage2 = new Sausage("B", 500, 200);
        Sausage sausage3 = new Sausage("C", 1000, 250);
        List<Sausage> sausageList = new ArrayList<>();
        sausageList.add(sausage1);
        sausageList.add(sausage2);
        sausageList.add(sausage3);
        sausageList.add(null);
        TaskUtilities<Sausage> sausageUtilities = new TaskUtilities<>();

        sausageUtilities.oneStreamWrite(sausageList, SAUSAGES_THERE);
        List<Optional<Sausage>> listFromFile = null;
        try {
            listFromFile = sausageUtilities.oneStreamRead(SAUSAGES_THERE);
        } catch (IOException e) {
            success = false;
            log.error("main() - unable to read file {}", SAUSAGES_THERE.getFileName());
        }
        listFromFile.stream()
                .filter(Optional::isPresent)
                .forEach(elem -> log.info("main() - NonNull object from the file: {}", elem.get()));

        log.info("Попытаемся прочитать из пустого файла:");
        try {
            sausageUtilities.oneStreamRead(EMPTY_FILE);
        } catch (IOException e) {
            success = false;
            log.error("main() - error during reading from the file {}", EMPTY_FILE.getFileName(), e);
        }
        log.info("main() - Демонстрация работы геттеров, возвращающих Optional:");
        Sausage sausageForNull = new Sausage(); //type = null
        //sausageForNull.setType("D"); //раскомментировать для выполнения первой ветки else.
        if (sausageForNull.getType().isPresent()) {
            log.info("main() - type of sausage is {}", sausageForNull.getType().get());
        } else {
            log.info("main() - No value in field \"type\" of {}", sausageForNull);
        }
        return success;
    }

    public static boolean demonstrateTaskOne() {
        boolean success = true;
        TaskOne taskOne = new TaskOne();
        List<UUID> uuids = taskOne.generateUUID(10000);
        long count = 0;
        long count2 = 0;
        try {
            taskOne.writeListToFile(uuids, PATH);
            count = taskOne.readAndCountUUIDs(PATH);
            log.info("main() - Using Streams. The number of UUIDs with sum of all digit > 100: {}.", count);
            count2 = taskOne.oldReadAndCountUUIDs(PATH);
            log.info("main() - Without Streams. The number of UUIDs with sum of all digit > 100: {}.", count2);
            if (count != count2) {
                success = false;
            }
        } catch (IOException e) {
            log.error("main() - Error during reading file {}.", PATH.getFileName(), e);
        }
        String dateOfDoomsDay = taskOne.makeDateOfDoomsday(count);
        String dateOfDoomsDay2 = taskOne.oldMakeDateOfDoomsday(count2);
        log.info("main() - Using Streams. Date of the end of the world: {}.", dateOfDoomsDay);
        log.info("main() - Without Streams. Date of the end of the world: {}.", dateOfDoomsDay2);
        return success;
    }
}
