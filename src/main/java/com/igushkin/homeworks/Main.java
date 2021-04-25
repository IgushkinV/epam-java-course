package com.igushkin.homeworks;


import com.igushkin.homeworks.lesson9.AnnotationProcessor;
import com.igushkin.homeworks.lesson9.exceptions.NoValueAnnotationException;
import com.igushkin.homeworks.lesson9.pojoClasses.Cat;
import com.igushkin.homeworks.lesson9.pojoClasses.Human;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Demonstrates how classes and methods work to handle @Entity and @Value annotations
 */
public class Main {

    final static Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        Human human = new Human();
        log.info("Creates Human object \"human\" with age={}, name={}", human.getAge(), human.getName());
        AnnotationProcessor processor = new AnnotationProcessor();
        try {
            processor.handlePojo(human);
            processor.handlePojo(new Cat());
        } catch (IllegalStateException | NoValueAnnotationException e) {
            log.error("main() - Error in class's annotations, check them!", e);
        }
        log.info("After processing fields of \"human\": age={}, name={}", human.getAge(), human.getName());
    }


}
