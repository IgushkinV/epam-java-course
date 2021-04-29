package igushkin.homeworks;

import igushkin.homeworks.lesson10.task2.MyNoNullArrayList;
import igushkin.homeworks.lesson10.task2.TaskUtilities;
import igushkin.homeworks.lesson10.task2.Sausage;
import igushkin.homeworks.lesson10.taskone.TaskOne;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Demonstrates solution of Task1 and Task2 of lesson 10 homework.
 */
@Slf4j
public class Main {

    public final static Path PATH = Path.of("src/main/resources/file.txt");
    public final static Path SAUSAGES_THERE = Path.of("src/main/resources/sausage.txt");
    public final static Path EMPTY_FILE = Path.of("src/main/resources/null.txt");

    public static void main(String[] args) {
        TaskOne taskOne = new TaskOne();
        List uuids = taskOne.generateUUID(10000);
        long count = 0;
        long count2 = 0;
        try {
            taskOne.writeListToFile(uuids, PATH);
            count = taskOne.readAndCountUUIDs(PATH);
            log.info("main() - Using Streams. The number of UUIDs with sum of all digit > 100: {}.", count);
            count2 = taskOne.oldReadAndCountUUIDs(PATH);
            log.info("main() - Without Streams. The number of UUIDs with sum of all digit > 100: {}.", count2);
        } catch (IOException e) {
            log.error("main() - Error during reading file {}.", PATH.getFileName(), e);
        }
        String dateOfDoomsDay = taskOne.makeDateOfDoomsday(count);
        String dateOfDoomsDay2 = taskOne.oldMakeDateOfDoomsday(count2);
        log.info("main() - Using Streams. Date of the end of the world: {}.", dateOfDoomsDay);
        log.info("main() - Without Streams. Date of the end of the world: {}.", dateOfDoomsDay2);

        log.info("*************** Task 2 demonstration (with 1 star) ***************");
        List<Sausage> sausageList = new ArrayList<>();
        Sausage opt1 = new Sausage("A", 500, 300);
        Sausage opt2 = new Sausage("B", 500, 200);
        Sausage opt3 = new Sausage("C", 1000, 250);
        sausageList.add(opt1);
        sausageList.add(opt2);
        sausageList.add(opt3);

        TaskUtilities<Sausage> sausageUtilities = new TaskUtilities<>();
        sausageUtilities.writeObjectsToFile(sausageList, SAUSAGES_THERE);
        Optional<List<Sausage>> optionalFromFile = sausageUtilities.readObjectsFromFile(SAUSAGES_THERE);
        List<Sausage> listFromFile = new ArrayList<>();
        if (optionalFromFile.isPresent()) {
            listFromFile = optionalFromFile.get();
        } else {
            log.warn("main() - No value present in passed list.");
        }
        for (Sausage sausage : listFromFile) {
            log.info("main() - {}", sausage.toString());
        }
        List<Sausage> noNullList = new MyNoNullArrayList<>();
        noNullList.add(null); //сообщение о том, что невозможно добавить null.

        sausageUtilities.readObjectsFromFile(EMPTY_FILE);

        Sausage sausageForNull = new Sausage();
        sausageForNull.setType("D"); //закомментировать для выполнения второй ветки else.
        if (sausageForNull.getType().isPresent()) {
            log.info("main() - type of sausage is {}", sausageForNull.getType().get());
        } else {
            log.info("main() - No value in field \"type\" of {}", sausageForNull);
        }
    }
}
