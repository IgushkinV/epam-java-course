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

import static org.junit.jupiter.api.Assertions.*;


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
        String expected = "Not So Strange";

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
}
