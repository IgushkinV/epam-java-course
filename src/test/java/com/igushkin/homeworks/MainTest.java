package com.igushkin.homeworks;

import org.junit.jupiter.api.Test;

import java.nio.file.NotDirectoryException;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void demonstrateThreeStarsReturnsTwo() {
        long expected = 2;
        long actual = Main.demonstrateThreeStars(Main.DIRECTORY);
        assertEquals(expected, actual);
    }

    @Test
    void demonstrateTwoStarsReturnsFive() {
        int expected = 5;
        int actual = Main.demonstrateTwoStars(Main.PATH);
        assertEquals(expected, actual);
    }
}