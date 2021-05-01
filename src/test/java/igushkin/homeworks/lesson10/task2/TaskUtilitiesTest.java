package igushkin.homeworks.lesson10.task2;

import igushkin.homeworks.Main;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests TaskUtilities class methods.
 */
class TaskUtilitiesTest {

    private static List<Sausage> sausageList = new ArrayList<>();
    private static TaskUtilities<Sausage> taskUtilities;

    @BeforeAll
    public static void prepare() {
        taskUtilities = new TaskUtilities<>();
        Sausage s1 = new Sausage("S1", 10, 10);
        Sausage s2 = new Sausage("S2", 20, 20);
        sausageList.add(s1);
        sausageList.add(s2);
        sausageList.add(null);
    }

    @Test
    public void readObjectsFromFileReadsTheeObjects() throws IOException {
        int expected = 3;
        taskUtilities.oneStreamWrite(sausageList, Main.SAUSAGES_THERE);

        List<Optional<Sausage>> listFromFile = taskUtilities.oneStreamRead(Main.SAUSAGES_THERE);
        int actual = listFromFile.size();

        assertEquals(expected, actual);

    }

    @Test
    public void writeObjectsToFileWritesObjectInThreeLines() throws IOException {
        long expected = 3;

        taskUtilities.oneStreamWrite(sausageList, Main.SAUSAGES_THERE);
        long actual = Files.lines(Main.SAUSAGES_THERE).count();

        assertEquals(expected, actual);

    }
}