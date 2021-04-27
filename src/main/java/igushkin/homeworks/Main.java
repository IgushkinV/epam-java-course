package igushkin.homeworks;

import igushkin.homeworks.lesson10.task2.FileUtilities;
import igushkin.homeworks.lesson10.task2.Sausage;
import igushkin.homeworks.lesson10.taskone.TaskOne;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Demonstrates methods from TaskOne.java
 */
@Slf4j
public class Main {

    public final static Path PATH = Path.of("src/main/resources/file.txt");
    public final static Path SAUSAGES = Path.of("src/main/resources/sausage.txt");

    public static void main(String[] args) {
        TaskOne taskOne = new TaskOne();
        List uuids = taskOne.generateUUID(10000);
        long count = 0;
        long count2 = 0;
        try {
            taskOne.writeListToFile(uuids, PATH);
            count = taskOne.readAndCountUUIDs(PATH);
            log.info("main() - Using Streams. The number of UUIDs with sum of all digit > 100: {}", count);
            count2 = taskOne.oldReadAndCountUUIDs(PATH);
            log.info("main() - Without Streams. The number of UUIDs with sum of all digit > 100: {}", count2);
        } catch (IOException e) {
            log.error("main() - Error during reading file {}", PATH.getFileName(), e);
        }
        String dateOfDoomsDay = taskOne.makeDateOfDoomsday(count);
        String dateOfDoomsDay2 = taskOne.oldMakeDateOfDoomsday(count2);
        log.info("main() - Using Streams. Date of the end of the world: {}", dateOfDoomsDay);
        log.info("main() - Without Streams. Date of the end of the world: {}", dateOfDoomsDay2);

        log.info("*************** Task 2 demonstration (with 1 star) ***************");
        List<Sausage> sausageList = new ArrayList<>();
        sausageList.add(new Sausage("A", 500, 300));
        sausageList.add(new Sausage("B", 500, 200));
        sausageList.add(new Sausage("C", 1000, 250));

        FileUtilities.writeObjectsToFile(sausageList, SAUSAGES);
        List<Sausage> readList = FileUtilities.readObjectsFromFile(SAUSAGES);
        for (Sausage sausage : readList) {
            log.info(sausage.toString());
        }
    }
}
