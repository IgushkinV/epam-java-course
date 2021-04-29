package com.igushkin.homeworks;


import com.igushkin.homeworks.lesson9.AnnotationProcessor;
import com.igushkin.homeworks.lesson9.exceptions.NoValueAnnotationException;
import com.igushkin.homeworks.lesson9.fileUtilities.FileUtilities;
import com.igushkin.homeworks.lesson9.pojoClasses.Human;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Demonstrates how classes and methods work to handle @Entity and @Value annotations
 */
public class Main {

    final static Logger log = LoggerFactory.getLogger(Main.class);

    private final static Path PATH = Path.of("src/main/resources/file.txt");

    public static void main(String[] args) {
        AnnotationProcessor processor = new AnnotationProcessor();
        List<Map<String, String>> dataSetList;
        try {
            dataSetList = FileUtilities.readEntriesFromFile(PATH);
        } catch (IOException e) {
            log.error("main() - error during reading data from file", e);
            return;
        }
        List<Human> humanList = new ArrayList<>();
        try {
            for (Map<String, String> dataSet : dataSetList) {
                processor.setPathValueMap(dataSet);
                Human human = new Human();
                processor.handlePojo(human);
                humanList.add(human);
            }
        } catch (IllegalStateException | NoValueAnnotationException e) {
            log.error("main() - Error in class's annotations, check them!", e);
        }
        log.info("New fields values of Humans are: ");
        for (Human human : humanList) {
            log.info("age={}, name={}", human.getAge(), human.getName());
        }
    }
}
