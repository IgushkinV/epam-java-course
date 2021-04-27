package igushkin.homeworks.lesson10.task2;

import igushkin.homeworks.Main;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.List;

@Slf4j
public class FileUtilities<T> {

    public static List<Sausage> readObjectsFromFile(Path path) {
        List<Sausage> readList = null;
        try {
            readList = (List<Sausage>) convertFromBytes(Files.readAllBytes(path));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return readList;
    }

    public static void writeObjectsToFile(List<Sausage> sausageList, Path path) {
        try {
            byte[] bytes = convertToBytes(sausageList);
            Files.write(path, bytes);
        } catch (IOException e) {
            log.error("writeObjectsToFile() - error during encoding and writing to the file");
        }
    }

    private static byte[] convertToBytes(Object object) {
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

    private static byte[] getEncode(byte[] bytes) {
        return Base64.getEncoder().encode(bytes);
    }

    private static Object convertFromBytes(byte[] bytes) throws IOException, ClassNotFoundException {
        Object obj = null;
        try (ByteArrayInputStream bis = new ByteArrayInputStream(getDecode(bytes));
             ObjectInputStream in = new ObjectInputStream(bis)) {
            obj = in.readObject();
        } catch (IOException e) {
            log.error("convertFromBytes() - error, during reading from ObjectInputStream", e);
        } catch (ClassNotFoundException e) {
            log.error("convertFromBytes() - error, during reading objects with ObjectInputStream", e);
        }
        return obj;
    }

    private static byte[] getDecode(byte[] bytes) {
        return Base64.getDecoder().decode(bytes);
    }
}
