package Igushkin.Lesson2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StorageTest {

    Storage<String> testStorage;
    String testString;

    @BeforeEach
    void setUp() {
        testStorage = new Storage<>();
        testString = "test";
    }

    @Test
    void addReallyAddingElementToStorage() throws NegativeIndexException {
        testStorage.add(testString);
        assertTrue(testStorage.getLast() != null);
    }

    @Test
    void addThrowsMyNullElementExceptionWhenAddingNull() {
        assertThrows(MyNullElementException.class, () ->
                testStorage.add(null));
    }
    @Test
    void creationNewStorageFromArrayReallyAddsAllElementsFromArray() throws NegativeIndexException {
        String[] strings = new String[]{"First", "Second", "3rd"};
        Storage<String> actualStorage = new Storage<>(strings);
        assertEquals(strings[0], actualStorage.get(0));
        assertEquals(strings[1], actualStorage.get(1));
        assertEquals(strings[2], actualStorage.get(2));
    }

    @Test
    void addElementExpandStorageWhenFull() throws NegativeIndexException {
        //StorageCapacity is 10 by default.
        String[] strings = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        String expected = "11";
        Storage<String> actualStorage = new Storage<>(strings);
        actualStorage.add("11");
        String actual = actualStorage.get(11);
        assertEquals(expected, actual);
    }
    @Test
    void deleteReallyDeletesLastElementFromStorage() throws NegativeIndexException {
        testStorage.add("First");
        testStorage.add("Second");
        testStorage.delete();
        String expectedResult = "First";
        String actualResult = testStorage.getLast();
        assertEquals(expectedResult,actualResult);
    }

    @Test
    public void deleteThrowsNegativeIndexExceptionIfDeleteFromEmptyStorage()  {
        assertThrows(NegativeIndexException.class, () -> {
            testStorage.delete();
        }, "Удаление из пустого хранилища!");
    }

    @Test
    void clearReturnsStorageWithOnlyNulls() throws NegativeIndexException {
        testStorage.add(testString);
        testStorage.clear();
        assertNull(testStorage.get(0));
    }

    @Test
    void getLastReturnsLastAddedElement() throws NegativeIndexException {
        testStorage.add("1");
        testStorage.add("2");
        String expected = "2";
        String actual = testStorage.getLast();
        assertEquals(expected, actual);
    }
    @Test
    void getLastThrowsNegativeIndexExceptionWhenStorageIsEmpty() {
        assertThrows(NegativeIndexException.class,
                () -> testStorage.getLast(),
                "Попытка получить последний элемент из пустого хранилища!");
    }

    @Test
    void getReturnsElementFromStorageByIndex() throws NegativeIndexException {
        testStorage.add(testString);
        String expected = testString;
        String actual = testStorage.get(0);
        assertEquals(expected, actual);
    }

    @Test
    void getThrowsNegativeIndexExceptionWhenPassingNegativeIndex() {
        testStorage.add(testString);
        assertThrows(NegativeIndexException.class,
                () -> testStorage.get(-1),
                "Запрос элемента с отрицательным индексом!");
    }

    @Test
    void getReturnsCorrectElementAfterCacheMovesElements() throws NegativeIndexException {
        testStorage.add("0");
        testStorage.add("1");
        testStorage.add("2");
        testStorage.add("3");
        testStorage.get(1);
        String expected = "3";
        assertEquals(expected, testStorage.get(3));

    }
}