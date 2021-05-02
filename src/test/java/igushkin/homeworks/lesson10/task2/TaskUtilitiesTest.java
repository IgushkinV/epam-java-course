package igushkin.homeworks.lesson10.task2;

import igushkin.homeworks.Main;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Tests TaskUtilities class methods.
 */
@Slf4j
class TaskUtilitiesTest {

    private List<Sausage> sausageList = new ArrayList<>();
    private TaskUtilities<Sausage> taskUtilities;

    @BeforeEach
    public void setUp() {
        this.taskUtilities = new TaskUtilities<>();
        Sausage s1 = new Sausage("S1", 10, 10);
        Sausage s2 = new Sausage("S2", 20, 20);
        sausageList.add(s1);
        sausageList.add(s2);
        sausageList.add(null);
    }

    @Test
    public void readObjectsFromFileReadsTheeObjects() {
        int expected = 3;
        taskUtilities.oneStreamWrite(sausageList, Main.SAUSAGES_THERE);

        List<Optional<Sausage>> listFromFile = null;
        try {
            listFromFile = taskUtilities.oneStreamRead(Main.SAUSAGES_THERE);
        } catch (IOException e) {
            fail("Error during reading the file.", e);
        }
        int actual = listFromFile.size();

        assertEquals(expected, actual);

    }

    @Test
    public void writeObjectsToFileWritesObjectInThreeLines() {
        long expected = 3;

        taskUtilities.oneStreamWrite(sausageList, Main.SAUSAGES_THERE);
        long actual = 0;
        try {
            actual = Files.lines(Main.SAUSAGES_THERE).count();
        } catch (IOException e) {
            fail("Error during writing to the file.", e);
        }

        assertEquals(expected, actual);

    }
}