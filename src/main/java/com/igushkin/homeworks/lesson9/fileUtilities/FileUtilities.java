package com.igushkin.homeworks.lesson9.fileUtilities;

import com.igushkin.homeworks.lesson9.annotations.Entity;
import com.igushkin.homeworks.lesson9.exceptions.WrongDataFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Provides methods to process data files.
 */
public class FileUtilities {
    final static Logger log = LoggerFactory.getLogger(FileUtilities.class);

    /**
     * Reads data file, spilt it to blocks, separated by an empty line.
     * Each block is sequence of lines with key and value separated with "=".
     *
     * @param fullPath the path to the data file.
     * @return The maps produced from the blocks.
     * @throws IOException when unable to read the data file.
     */
    public static List<Map<String, String>> readEntriesFromFile(Path fullPath) throws IOException {
        String delimiter = System.lineSeparator() + System.lineSeparator();
        List<Map<String, String>> entryList = new ArrayList<>();
        String wholeFile = Files.readString(fullPath);
        String[] blocks = wholeFile.split(delimiter);
        log.trace("Count of blocks is {}", blocks.length);
        for (String block : blocks) {
            Map<String, String> entityMap = new HashMap<>();
            String[] fieldLines = block.split(System.lineSeparator());
            for (String fieldLine : fieldLines) {
                String[] keyValueParts = fieldLine.split("=", 2);
                if (keyValueParts.length == 2) {
                    entityMap.put(keyValueParts[0], keyValueParts[1]);
                    log.trace("Field parts: {}, {}", keyValueParts[0], keyValueParts[1]);
                } else {
                    throw new WrongDataFormatException(
                            "Wrong data format. \"=\" not found in source line: " + fieldLine);
                }
            }
            entryList.add(entityMap);
        }
        return entryList;
    }

    /**
     * Counts the number of classes marked with @Entity annotation
     *
     * @param name
     * @return
     */
    public static int countAnnotations(String name) throws ClassNotFoundException, IOException {
        final String projectClassesDirectory = "target\\classes";
        List<File> fileList = new ArrayList<>();
        try {
            fileList = Files.walk(Paths.get(projectClassesDirectory))
                    .filter(Files::isRegularFile)
                    .filter(path1 -> path1.toString().endsWith(".class"))
                    .map(path1 -> new File(path1.toString()))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Теперь создаем объект этого класса через класслоадер и проверяем наличие аннотации.
        for (File file : fileList) {
            String[] parts = file.getName().split("\\.");
            String className = parts[0];
            String extension = parts[1];

            String fqnOfClass = file.getAbsolutePath().split("classes\\\\")[1]
                    .replaceAll("\\\\", "\\.")
                    .replace(".class", "");

            Class<?> clazz = ClassLoader.getSystemClassLoader().loadClass(fqnOfClass);
            Annotation annotation = clazz.getAnnotation(Entity.class);
            System.out.println(Objects.nonNull(annotation) + " - " + clazz.getName());
        }


        return -1;
    }
}
