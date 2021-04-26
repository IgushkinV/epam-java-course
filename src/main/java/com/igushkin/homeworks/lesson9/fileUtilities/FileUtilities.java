package com.igushkin.homeworks.lesson9.fileUtilities;

import com.igushkin.homeworks.lesson9.exceptions.WrongDataFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Provides methods to process data files
 */
public class FileUtilities {
    final static Logger log = LoggerFactory.getLogger(FileUtilities.class);

    /**
     * Reads data file, spilt it to blocks, separated by an empty line.
     * Each block is sequence of lines with key and value separated with "=".
     * @param fullPath the path to the data file
     * @return The maps produced from the blocks
     * @throws IOException when unable to read the data file
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
        return  entryList;
    }
}
