package com.igushkin.homeworks;

import com.igushkin.homeworks.lesson9.AnnotationProcessor;
import com.igushkin.homeworks.lesson9.annotations.Entity;
import com.igushkin.homeworks.lesson9.annotations.Value;
import com.igushkin.homeworks.lesson9.exceptions.NoValueAnnotationException;
import com.igushkin.homeworks.lesson9.exceptions.TypeUnsupportedException;
import com.igushkin.homeworks.lesson9.fileUtilities.FileUtilities;
import com.igushkin.homeworks.lesson9.pojoClasses.Cat;
import com.igushkin.homeworks.lesson9.pojoClasses.Human;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        TestWithOnlyEntity test = new TestWithOnlyEntity();

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
        Map<String, String> firstPathValueMap = FileUtilities.readEntriesFromFile(Path.of(fullPath)).get(0);
        processor.setPathValueMap(firstPathValueMap);
        TestSetFromFileUsingFieldAnnotation test = new TestSetFromFileUsingFieldAnnotation();
        processor.handlePojo(test);
        int actual = test.age;

        assertEquals(expected,actual);
    }
    @Test
    public void testSettingValueFromFileUsingSetterAnnotation() throws IOException {
        String fullPath = "src/main/resources/file.txt";
        String expected = "FirstName";
        Map<String, String> firstPathValueMap = FileUtilities.readEntriesFromFile(Path.of(fullPath)).get(0);
        processor.setPathValueMap(firstPathValueMap);
        TestSetFromFileUsingSetterAnnotation test = new TestSetFromFileUsingSetterAnnotation();
        processor.handlePojo(test);
        String actual = test.name;

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Throws IllegalStateException when has no @Entity but has one @Value on method")
    public void throwsIllegalStateException() {
        TestWithOnlyValueOnMethod test = new TestWithOnlyValueOnMethod();
        assertThrows(IllegalStateException.class, () ->
                processor.handlePojo(test));
    }

    @Entity
    class TestWithOnlyEntity{
        int age;
        String name;
        public void setAge(int age) {}
        public void setName(String name) {}
    }

    class TestWithOnlyValueOnMethod {
        int age;
        String name;
        @Value
        public void setAge (int age) {}
        public void setName (String name) {}
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
