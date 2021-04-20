package igushkin.lesson2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CacheTest {
    String testString;
    Cache<String> cache;
    CacheElement<String> testElement;


    @BeforeEach
    void setUp() {
        cache = new Cache<>(1);
        testString = "test";
        testElement = new CacheElement<>("test", 0);
    }

    @Test
    void addReallyAddingElementToCache() {
        cache.add("test",0);
        boolean actualResult = cache.isPresent(testElement);
        assertTrue(actualResult);

    }

    @Test
    void deleteReallyDeletesFromCache() {
        CacheElement<String> element = new CacheElement<>("test", 0);
        cache.add(testString,0);

        cache.delete(testString);
        boolean actualResult = cache.isPresent(element);

        assertFalse(actualResult);
    }

    @Test
    void isPresentByIndexReturnsTrueWhenElementIsPresent() {
        cache.add(testString,0);
        assertTrue(cache.isPresent(0));
    }

    @Test
    void isPresentByElementReturnsTrueWhenElementIsPresent() {
        CacheElement<String> element = new CacheElement<>("test", 0);
        cache.add(testString,0);

        assertTrue(cache.isPresent(element));
    }

    @Test
    void getReturnsCorrectElement() {
        CacheElement<String> expectedResult = new CacheElement<>(testString, 0);
        cache.add(testString,0);

        CacheElement<String> actualResult = cache.get(0);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void clearReturnsCacheWithOnlyNulls() {
        cache.add(testString, 0);
        cache.clear();
        assertNull(cache.get(0));
    }
}