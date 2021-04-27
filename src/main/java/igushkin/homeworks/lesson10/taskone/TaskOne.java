package igushkin.homeworks.lesson10.taskone;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Slf4j
public class TaskOne {

    /**
     * Generates UUIDs and fills List<UUID> with them.
     *
     * @param number how many UUID's need to generate.
     * @return List<UUID>
     */
    public List<UUID> generateUUID(int number) {
        log.info("generateUUID() - start of generation {} UUIDs, using Streams API", number);
        return Stream.generate(UUID::randomUUID)
                .limit(number)
                .collect(Collectors.toList());
    }

    /**
     * generates UUIDs and fills List<UUID> with them.
     *
     * @param number how many UUID's need to generate.
     * @return List<UUID>
     */
    public List<UUID> oldGenerateUUID(int number) {
        log.info("oldGenerateUUID() - start of generation {} UUIDs, using Java7", number);
        List<UUID> uuidList = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            uuidList.add(UUID.randomUUID());
        }
        return uuidList;
    }

    /**
     * Writes UUID from the passed list to the file.
     *
     * @param list that contains UUID to write to the file.
     * @param path contains path to the file.
     * @throws IOException when unable to write to the file.
     */
    public void writeListToFile(List<UUID> list, Path path) throws IOException {
        log.info("writeListToFile() - start of writing UUIDs to file, using Stream API");
        Files.writeString(path, list.stream()
                .map(Object::toString)
                .collect(Collectors.joining(System.lineSeparator())));
    }

    /**
     * Writes UUID from the passed list to the file.
     *
     * @param list that contains UUID to write to the file.
     * @param path contains path to the file.
     * @throws IOException when unable to write to the file.
     */
    public void oldWriteListToFile(List<UUID> list, Path path) {
        log.info("oldWriteListToFile() - start of writing UUIDs to file, using Java7");
        File file = new File(path.toString());
        if (file.exists()) {
            try (FileWriter writer = new FileWriter(file)) {
                for (UUID uuid : list) {
                    writer.write(uuid.toString() + System.lineSeparator());
                }
            } catch (IOException e) {
                log.error("oldWriteListToFile() - error during writing to the file", e);
            }
        }
    }

    /**
     * Reads UUIDs from the file and counts the number of UUIDs where the sum of digits is greater than 100.
     *
     * @param path to the file, that contains UUIDs.
     * @return long number of UUIDs where the sum of digits is grater than 100.
     * @throws IOException when unable to read the file.
     */
    public long readAndCountUUIDs(Path path) throws IOException {
        log.info("readAndCountUUIDs() - start to reading UUIDs from file and count, how many of them has " +
                "the sum of digits greater than 100, using Stream API.");
        long count = 0;
        try {
            count = Files.lines(path)
                    .mapToInt((line) -> line.chars()
                            .filter(Character::isDigit)
                            .mapToObj(Character::getNumericValue)
                            .reduce((n1, n2) -> n1 + n2).get())
                    .filter(n -> n > 100)
                    .count();
        } catch (IOException e) {
            log.error("readAndCountUUIDs() - error during reading from file {}", path.getFileName(), e);
        }
        return count;
    }

    /**
     * Reads UUIDs from the file and counts the number of UUIDs where the sum of digits is greater than 100.
     *
     * @param path to the file, that contains UUIDs.
     * @return long number of UUIDs where the sum of digits is grater than 100.
     * @throws IOException when unable to read the file.
     */
    public long oldReadAndCountUUIDs(Path path) {
        log.info("oldReadAndCountUUIDs() - start to reading UUIDs from file and count, how many of them has " +
                "the sum of digits greater than 100, using Java7.");
        long count = 0;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path.toString()))) {
            List<String> lines = new ArrayList<>();
            while (bufferedReader.ready()) {
                lines.add(bufferedReader.readLine());
            }
            for (String line : lines) {
                int sumOfDigitsInLine = 0;
                for (char ch : line.toCharArray()) {
                    if ('0' <= ch && ch <= '9') {
                        sumOfDigitsInLine += (ch - '0');
                    }
                }
                if (sumOfDigitsInLine > 100) {
                    count++;
                }
            }
        } catch (IOException e) {
            log.error("oldReadAndCountUUIDs() - error during reading file {}",
                    path.getFileName(), e);
        }
        return count;
    }

    /**
     * Calculates the date of the end of the world.
     *
     * @param number The number used to calculate the date of the end of the world:
     *               the first 2 digits of the number are used to calculate the month,
     *               the second 2 digits are used to calculate the day of the end of the world.
     * @return String, where the date of the end of the world presented in ISO date and time format.
     */
    public String makeDateOfDoomsday(long number) {
        log.info("makeDateOfDoomsday() - start to creating the date of the end of the world, using Stream API");
        return ZonedDateTime.now(ZoneId.of("UTC-8"))
                .plusMonths(Long.parseLong(String.format("%04d", (int) number).substring(0, 2)))
                .plusDays(Long.parseLong(String.format("%04d", (int) number).substring(2, 4)))
                .format(DateTimeFormatter.ISO_DATE_TIME);
    }

    /**
     * Calculates the date of the end of the world.
     *
     * @param number The number used to calculate the date of the end of the world:
     * the first 2 digits of the number are used to calculate the month,
     * the second 2 digits are used to calculate the day of the end of the world.
     * @return String, where the date of the end of the world presented in ISO date and time format.
     **/

    public String oldMakeDateOfDoomsday(long number) {
        log.info("oldMakeDateOfDoomsday() - start to creating the date of the end of the world, without Stream API");
        ZonedDateTime timeNow = ZonedDateTime.now(ZoneId.of("UTC-8"));
        String strNum = String.format("%04d", number);

        long monthsToPlus = (Long.parseLong((strNum).substring(0, 2)));
        long daysToPlus = (Long.parseLong((strNum).substring(2, 4)));
        timeNow = timeNow.plusMonths(monthsToPlus);
        timeNow = timeNow.plusDays(daysToPlus);
        String formattedTimeNow = timeNow.format(DateTimeFormatter.ISO_DATE_TIME);
        return formattedTimeNow;
    }
}
