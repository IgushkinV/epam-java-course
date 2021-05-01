package igushkin.homeworks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests Main class methods.
 */
class MainTest {


    @Test
    public void demonstrateTaskTwoReturnsTrue() {
        assertTrue(Main.demonstrateTaskTwo());
    }

    @Test
    public void demonstrateTaskOneReturnsTrue() {
        assertTrue(Main.demonstrateTaskOne());
    }
}