package com.igushkin.homeworks;

import com.igushkin.homeworks.lesson9.AnnotationProcessor;
import com.igushkin.homeworks.lesson9.annotations.Entity;
import com.igushkin.homeworks.lesson9.annotations.Value;
import com.igushkin.homeworks.lesson9.exceptions.NoValueAnnotationException;
import com.igushkin.homeworks.lesson9.exceptions.TypeUnsupportedException;
import com.igushkin.homeworks.lesson9.pojoClasses.Cat;
import com.igushkin.homeworks.lesson9.pojoClasses.Human;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests methods of {@link AnnotationProcessor} class.
 */
public class AnnotationProcessorTest {
    public AnnotationProcessor processor;
    public Human human;
    public Cat cat;

    @BeforeEach
    public void initialize() {
        processor = new AnnotationProcessor();
        cat = new Cat();
        human = new Human();
    }

    @Test
    public void throwsIllegalStateExceptionWhenClassHasNoEntityAnnotation() {
        assertThrows(IllegalStateException.class, () ->
                processor.handlePojo(cat));
    }

    @Test
    @DisplayName("throws NoValueAnnotationException when class has @Entity and no @Value")
    public void throwsNoValueAnnotationException() {
        @Entity
        class Test{
            int age;
            String name;
            public void setAge(int age) {}
            public void setName(String name) {}
        }
        Test test = new Test();

        assertThrows(NoValueAnnotationException.class, () ->
                processor.handlePojo(test));
    }

    @Test
    public void setHumanNameFromFieldAnnotation() {
        String expected = "Strange";

        processor.handlePojo(human);
        String actual = human.getName();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Throws TypeUnsupportedException when wrong type value passed to @Value")
    public void throwsTypeUnsupportedException() {
        TestWithEntityAndWrongTypeValue test = new TestWithEntityAndWrongTypeValue();
        assertThrows(TypeUnsupportedException.class, () ->
                processor.handlePojo(test));
    }

    @Test
    public void testSettingValueFromFileUsingFieldAnnotation() throws IOException {
        String fullPath = "src/main/resources/file.txt";
        int expected = 123;
        processor.useValuesFromPath(Path.of(fullPath));
        TestSetFromFileUsingFieldAnnotation test = new TestSetFromFileUsingFieldAnnotation();
        processor.handlePojo(test);
        int actual = test.age;

        assertEquals(expected,actual);
    }
    @Test
    public void testSettingValueFromFileUsingSetterAnnotation() throws IOException {
        String fullPath = "src/main/resources/file.txt";
        String expected = "FirstName";
        processor.useValuesFromPath(Path.of(fullPath));
        TestSetFromFileUsingSetterAnnotation test = new TestSetFromFileUsingSetterAnnotation();
        processor.handlePojo(test);
        String actual = test.name;

        assertEquals(expected, actual);
    }


    @Entity
    class TestWithOnlyEntity{
        int age;
        String name;
        public void setAge(int age) {}
        public void setName(String name) {}
    }

    @Entity
    static class TestWithEntityAndWrongTypeValue{
        @Value("no")
        boolean isAdult;

        @Value("no")
        public void setIsAdult(boolean isAdult) {}
    }

    @Entity
    static class TestSetFromFileUsingFieldAnnotation{
        @Value(value = "50", path = "Age")
        int age;
        String name;
        public void setAge(int age) {}
        @Value(value = "First", path = "name")
        public void setName(String name) {}
    }

    @Entity
    static class TestSetFromFileUsingSetterAnnotation{
        int age;
        String name;
        public void setAge(int age) {}
        @Value(value = "First", path = "name")
        public void setName(String name) {}
    }

}
