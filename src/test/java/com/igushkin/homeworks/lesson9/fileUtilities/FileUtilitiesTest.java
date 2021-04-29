package com.igushkin.homeworks.lesson9.fileUtilities;

import com.igushkin.homeworks.Main;
import org.junit.jupiter.api.Test;

import java.nio.file.NotDirectoryException;

import static org.junit.jupiter.api.Assertions.*;

class FileUtilitiesTest {

    @Test
    void countAnnotationsReturnsTwo() throws NotDirectoryException {
        long expected = 2;
        long actual = FileUtilities.countAnnotations(Main.DIRECTORY);
        assertEquals(actual, expected);
    }

    @Test
    void countAnnotationsThrowsNotDirectoryException() {
        assertThrows(NotDirectoryException.class, () ->
                FileUtilities.countAnnotations("NotADirectory"));
    }
}