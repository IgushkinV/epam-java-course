package igushkin.homeworks;

import igushkin.homeworks.lesson10.taskone.TaskOne;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Demonstrates methods from TaskOne.java
 */
@Slf4j
public class Main {

    public final static Path PATH = Path.of("src/main/resources/file.txt");

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
    }
}
