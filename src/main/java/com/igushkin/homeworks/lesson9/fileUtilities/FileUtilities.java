package com.igushkin.homeworks.lesson9.fileUtilities;

import com.igushkin.homeworks.lesson9.annotations.Entity;
import com.igushkin.homeworks.lesson9.annotations.Value;
import com.igushkin.homeworks.lesson9.exceptions.WrongDataFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.nio.file.Files;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

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
     * Counts the number of classes marked with @Entity annotation.
     *
     * @param directory project classes directory.
     * @return count of classes, annotated with @Entity.
     */
    public static long countAnnotations(String directory) throws NotDirectoryException {
        if (!Files.isDirectory(Path.of(directory))) {
            throw new NotDirectoryException("No directory passed!");
        }
        List<File> fileList = getFileList(directory);
        long count = fileList.stream()
                .map(FileUtilities::getFqnOfClass)
                .filter(FileUtilities::isAnnotated)
                .count();
        return count;
    }

    /**
     * Checks if the class has @Entity.
     *
     * @param fqnOfClass Fully qualified name of the class.
     * @return true if the class has @Entity.
     */
    private static boolean isAnnotated(String fqnOfClass) {
        Class<?> clazz = null;
        try {
            clazz = ClassLoader.getSystemClassLoader().loadClass(fqnOfClass);
        } catch (ClassNotFoundException e) {
            log.error("isAnnotated() - error during loading class {}", fqnOfClass);
        }
        Annotation annotation = clazz.getAnnotation(Entity.class);
        return Objects.nonNull(annotation);
    }

    /**
     * Makes fully qualified name of the class.
     *
     * @param file .class file
     * @return String which presents fully qualified name of the class.
     */
    private static String getFqnOfClass(File file) {
        return file.getAbsolutePath().split("classes\\\\")[1]
                .replaceAll("\\\\", "\\.")
                .replace(".class", "");
    }

    /**
     * Searches for all .class files in the passed folder
     *
     * @param projectClassesDirectory directory with .class files.
     * @return list of files.
     */
    private static List<File> getFileList(String projectClassesDirectory) {
        List<File> fileList = new ArrayList<>();
        try {
            fileList = Files.walk(Paths.get(projectClassesDirectory))
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".class"))
                    .map(path -> new File(path.toString()))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            log.error("getFileList() - error during accessing to files in the directory {} ", projectClassesDirectory);
        }
        return fileList;
    }
}
