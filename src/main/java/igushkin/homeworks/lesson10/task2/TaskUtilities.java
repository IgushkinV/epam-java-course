package igushkin.homeworks.lesson10.task2;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Slf4j
public class TaskUtilities<T> {

    public Optional<List<T>> readObjectsFromFile(Path path) {
        Optional<List<T>> readList = Optional.empty();
        try {
            Optional<T> temp = convertFromBytes(Files.readAllBytes(path));
            if (temp.isEmpty()) {
                log.warn("readObjectsFromFile() - No bytes received from convertFromBytes(). " +
                        "Optional.empty() will be returned.");
                return readList;
            }
            readList = Optional.of((List<T>) convertFromBytes(Files.readAllBytes(path)).get());
        } catch (IOException e) {
            log.error("readObjectsFromFile() - error during reading objects from the file {}.", path.getFileName(), e);
        }
        return readList;
    }

    public void writeObjectsToFile(List<T> tList, Path path) {
        try {
            byte[] bytes = convertToBytes(tList);
            Files.write(path, bytes);
        } catch (IOException e) {
            log.error("writeObjectsToFile() - error during encoding and writing to the file.");
        }
    }

    private byte[] convertToBytes(Object object) {
        byte[] encode = null;
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream out = new ObjectOutputStream(bos)) {
            out.writeObject(object);
            encode = getEncode(bos.toByteArray());
        } catch (IOException e) {
            log.error("convertToBytes() - error during writing to ObjectOutputStream.");
        }
        return encode;
    }

    private byte[] getEncode(byte[] bytes) {
        return Base64.getEncoder().encode(bytes);
    }

    private Optional<T> convertFromBytes(byte[] bytes) {
        Optional<T> obj = Optional.empty();
        try (ByteArrayInputStream bis = new ByteArrayInputStream(getDecode(bytes));
             ObjectInputStream in = new ObjectInputStream(bis)) {
            obj = Optional.of((T) in.readObject());
        } catch (IOException e) {
            log.error("convertFromBytes() - error, during reading from ObjectInputStream.", e);
        } catch (ClassNotFoundException e) {
            log.error("convertFromBytes() - error, during reading objects with ObjectInputStream.", e);
        }
        return obj;
    }

    private byte[] getDecode(byte[] bytes) {
        return Base64.getDecoder().decode(bytes);
    }
}
