package Igushkin.Homeworks.lesson7;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;

@Slf4j
public class CustomClassLoader extends ClassLoader {
    private String path;
    private final String fileExtension = ".class";

    public CustomClassLoader(String path) {
        this.path = path;
    }

    /**
     * Searches for the file containing the passed class by its full path.
     * @param className
     * @return
     */
    protected Class findClass(String className) {
        String fullName = this.path + className + this.fileExtension;
        byte[] bytes = null;
        try {
            bytes = this.loadClassFromFile(fullName);
        } catch (IOException e) {
            log.warn("Error while reading from file {}", fullName, e);
        }
        return this.defineClass(className, bytes, 0, bytes.length);
    }

    /**
     * Reads bytes from the specified file.
     * @param fullFileName The expected full file name
     * @return byte[]
     * @throws IOException
     */
    private byte[] loadClassFromFile(String fullFileName) throws IOException {
        try (FileInputStream inputStream = new FileInputStream(fullFileName)) {
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer, 0, size);
            return buffer;
        }
    }
}
