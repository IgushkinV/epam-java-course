package igushkin.homeworks.lesson10.taskone;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests methods TaskOne
 */
class TaskOneTest {

    private static final Path PATH = Path.of("src/main/resources/file.txt");

    private TaskOne taskOne;

    @BeforeEach
    public void initialize() {
        this.taskOne = new TaskOne();
    }

    @Test
    public void testGenerateUUIDReturnsListWithTenUUID() {
        int expected = 10;
        List<UUID> list;

        list = taskOne.oldGenerateUUID(10);
        int actual = list.size();

        assertEquals(expected, actual);
    }

    @Test
    public void testOldGenerateUUIDReturnsListWithTenUUID() {
        int expected = 10;
        List<UUID> list;

        list = taskOne.oldGenerateUUID(10);
        int actual = list.size();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Tests writeListToFile() correct writes UUIDs to files and test return 1st from file")
    public void testWriteListToFileCorrectWriteToFile() throws IOException {
        List<UUID> list = taskOne.generateUUID(10);
        String expected = list.get(0).toString();

        taskOne.writeListToFile(list ,PATH);
        String actual = Files.lines(PATH).limit(1).collect(Collectors.joining());

        assertEquals(expected, actual);
    }

    @Test
    public void writeListToFileWritesTenLines() throws IOException {
        List<UUID> list = taskOne.generateUUID(10);
        long expected = 10;

        taskOne.writeListToFile(list ,PATH);
        long actual = Files.lines(PATH).count();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Tests oldWriteListToFile() correct writes UUIDs to files and test return 1st from file")
    public void testOldWriteListToFileCorrectWriteToFile() throws IOException {
        List<UUID> list = taskOne.oldGenerateUUID(10);
        String expected = list.get(0).toString();

        taskOne.oldWriteListToFile(list ,PATH);
        String actual = Files.lines(PATH).limit(1).collect(Collectors.joining());

        assertEquals(expected, actual);
    }

    @Test
    public void oldWriteListToFileWritesTenLines() throws IOException {
        List<UUID> list = taskOne.oldGenerateUUID(10);
        long expected = 10;

        taskOne.oldWriteListToFile(list ,PATH);
        long actual = Files.lines(PATH).count();

        assertEquals(expected, actual);
    }

    @Test
    public void testReadAndCountUUIDsReturn() throws IOException {
        String testString = "0a1b2c3d4e5f9999999999";//sum of digits = 105
        String testString2 = "0a1b2c3d4e5f999999999"; //sum of digits = 96
        long expected = 1;
        Files.writeString(PATH, testString + System.lineSeparator() + testString2);

        long actual = taskOne.readAndCountUUIDs(PATH);

        assertEquals(expected, actual);
    }

    @Test
    public void testOldReadAndCountUUIDsReturn() throws IOException {
        String testString = "0a1b2c3d4e5f9999999999";//sum of digits = 105
        String testString2 = "0a1b2c3d4e5f999999999"; //sum of digits = 96
        long expected = 1;
        Files.writeString(PATH, testString + System.lineSeparator() + testString2);

        long actual = taskOne.oldReadAndCountUUIDs(PATH);

        assertEquals(expected, actual);
    }

    @Test
    public void testMakeDateOfDoomsdayChangeTodayDateToOneMonth() {
        ZonedDateTime nowZoneMinus8 = ZonedDateTime.now(ZoneId.of("UTC-8"));
        Month expected = nowZoneMinus8.plusMonths(1).getMonth();

        Month actual = taskOne.makeDateOfDoomsday(100).getMonth();

        assertEquals(expected, actual);
    }

    @Test
    public void testOldMakeDateOfDoomsdayChangeTodayDateToOneMonth() {
        ZonedDateTime nowZoneMinus8 = ZonedDateTime.now(ZoneId.of("UTC-8"));
        Month expected = nowZoneMinus8.plusMonths(1).getMonth();

        Month actual = taskOne.oldMakeDateOfDoomsday(100).getMonth();

        assertEquals(expected, actual);
    }
}