package igushkin.lesson2;

import igushkin.lesson2.exceptions.MyNullElementException;
import igushkin.lesson2.exceptions.NegativeIndexException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StorageTest {

    Storage<String> testStorage;
    String testString;

    @BeforeEach
    void setUp() {
        testStorage = new Storage<>();
        testString = "test";
    }

    @Test
    void addReallyAddingElementToStorage() {
        testStorage.add(testString);
        assertNotNull(testStorage.getLast());
    }

    @Test
    void addThrowsMyNullElementExceptionWhenAddingNull() {
        assertThrows(MyNullElementException.class, () ->
                testStorage.add(null));
    }
    @Test
    void creationNewStorageFromArrayReallyAddsAllElementsFromArray() {
        String[] strings = new String[]{"First", "Second", "3rd"};

        Storage<String> actualStorage = new Storage<>(strings);

        assertEquals(strings[0], actualStorage.get(0));
        assertEquals(strings[1], actualStorage.get(1));
        assertEquals(strings[2], actualStorage.get(2));
    }

    @Test
    void addElementExpandStorageWhenFull() {
        //StorageCapacity is 10 by default.
        String[] strings = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        String expected = "11";

        Storage<String> actualStorage = new Storage<>(strings);
        actualStorage.add("11");
        String actual = actualStorage.get(11);

        assertEquals(expected, actual);
    }
    @Test
    void deleteReallyDeletesLastElementFromStorage() {
        testStorage.add("First");
        testStorage.add("Second");
        String expectedResult = "First";

        testStorage.delete();
        String actualResult = testStorage.getLast();

        assertEquals(expectedResult,actualResult);
    }

    @Test
    public void deleteThrowsMyNullElementExceptionIfDeleteFromEmptyStorage()  {
        assertThrows(MyNullElementException.class, () -> {
            testStorage.delete();
        }, "Trying to delete from empty store!");
    }

    @Test
    void clearReturnsEmptyStorageThenGetTrowsMyNullElementException() {
        testStorage.add(testString);
        testStorage.clear();

        assertThrows(MyNullElementException.class,
                () -> testStorage.get(0),
                "Trying to get item from the empty store!");
    }

    @Test
    void getLastReturnsLastAddedElement() {
        testStorage.add("1");
        testStorage.add("2");
        String expected = "2";

        String actual = testStorage.getLast();

        assertEquals(expected, actual);
    }
    @Test
    void getLastThrowsMyNullElementExceptionWhenStorageIsEmpty() {
        assertThrows(MyNullElementException.class,
                () -> testStorage.getLast(),
                "Trying to get the last item from the empty store!");
    }

    @Test
    void getReturnsElementFromStorageByIndex() {
        String expected = testString;

        testStorage.add(testString);
        String actual = testStorage.get(0);

        assertEquals(expected, actual);
    }

    @Test
    void getThrowsNegativeIndexExceptionWhenPassingNegativeIndex() {
        testStorage.add(testString);
        assertThrows(NegativeIndexException.class,
                () -> testStorage.get(-1),
                "Requesting an item with a negative index!");
    }

    @Test
    void getReturnsCorrectElementAfterCacheMovesElements() {
        testStorage.add("0");
        testStorage.add("1");
        testStorage.add("2");
        testStorage.add("3");
        String expected = "3";

        //Adding to Cache occurs upon receipt from Storage
        testStorage.get(0);
        testStorage.get(1);
        testStorage.get(2);
        testStorage.get(3);
        //Rearranging the item in the Cache last and moving those following it one position.
        testStorage.get(1);

        assertEquals(expected, testStorage.get(3));
    }

    @Test
    void getReturnsCorrectElementWhenCacheIsFull() {
        //The cache capacity is always 10.
        for (int i = 0; i < 11; i++) {
            //Adds 11 items to the cache. The first one added disappears.
            testStorage.add(i+"");
            testStorage.get(i);
        }
        String expected = "0";

        String actual = testStorage.get(0);

        assertEquals(expected, actual);
    }

    @Test
    void getThrowsIndexOutOfBoundsExceptionWhetPassedIndexLagerThanCapacity() {
        testStorage.add(testString);
        assertThrows(IndexOutOfBoundsException.class,
                () -> testStorage.get(15),
                "Request for an item whose index exceeds the storage capacity!");
    }
}