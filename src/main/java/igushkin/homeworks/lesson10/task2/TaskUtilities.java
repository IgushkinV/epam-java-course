package igushkin.homeworks.lesson10.task2;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Provides utility methods for writing objects to and reading objects from a file
 * The object class must implement the Serializable interface.
 *
 * @param <T> must implement the Serializable interface.
 */
@Slf4j
public class TaskUtilities<T> {

    /**
     * Writes objects from the passed list to a file using Base64 encoding.
     * Each object will be written on a new line.
     *
     * @param listOfObjects The list of objects to be written to the file.
     */
    public void oneStreamWrite(List<T> listOfObjects, Path path) {
        Base64.Encoder encoder = Base64.getEncoder();
            String strToWrite = listOfObjects.stream()
                    .map(obj -> {
                        log.debug("oneStreamWrite() - writing object {}", obj);
                        byte[] bytes = null;
                        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
                             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
                            oos.writeObject(obj);
                            bytes = bos.toByteArray();
                        } catch (IOException e) {
                            log.error("oneStreamWrite() - error during converting object {} to byte[]", obj, e);
                        }
                        return bytes;
                    })
                    .map(encoder::encodeToString)
                    .collect(Collectors.joining(System.lineSeparator()));
        try {
            Files.writeString(path, strToWrite);
        } catch (IOException e) {
            log.error("oneStreamWrite() - error during writing the string to the file", e);
        }
    }

    /**
     * Reads objects from a file using Base64 decoding. Each object must be written on a separate line.
     *
     * @param path Path to the file, which contains encoded objects.
     * @return List of Optionals. Object in Optional may be a null.
     * @throws IOException when unable to read lines form passed file.
     */
    @SuppressWarnings("unchecked")
    public List<Optional<T>> oneStreamRead(Path path) throws IOException {
        Base64.Decoder decoder = Base64.getDecoder();
        log.debug("oneStreamRead() - Lines count: {}", Files.lines(path).count());
        return Files.lines(path)
                .peek(line -> log.debug("oneStreamRead() - Line to parse: {}", line))
                .map(decoder::decode)
                .map(bytes -> {
                    T obj = null;
                    try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
                         ObjectInputStream ois = new ObjectInputStream(bis)) {
                        obj = (T) ois.readObject();
                        log.debug("oneStreamRead() - Object from file is: {}", obj);
                    } catch (IOException e) {
                        log.error("oneStreamRead() - Error during reading file {}", path, e);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    return obj;
                })
                .map(Optional::ofNullable)
                .peek(obj -> log.debug("oneStreamRead() - {} placed to list", obj))
                .collect(Collectors.toList());
    }
}
