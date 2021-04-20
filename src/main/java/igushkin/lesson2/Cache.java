package igushkin.lesson2;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Array;

/**
 * A typed class for creating a cache. The cache does not contain the same items.
 *
 * @param <T>
 */
@Slf4j
public class Cache<T> {

    private int capacity;
    private CacheElement<T>[] cache;
    /**
     * Field. Contains the index of the last non-null item in the cache. If the cache is empty, it is -1.
     */
    private int size;

    /**
     * Constructor. Creates a cache object with the specified capacity.
     *
     * @param capacity cache capacity.
     */
    @SuppressWarnings("unchecked")
    public Cache(int capacity) {
        this.capacity = capacity;
        this.cache = (CacheElement<T>[]) new CacheElement[capacity];
        this.size = -1;
        log.debug("Creating cache. Cache capacity is {}", this.capacity);
    }

    /**
     * Adds element to the cache. Checks for duplicates, does not add duplicate items.
     * If trying to add an item, it will re-mark it as most recently used.
     * Adding occurs to the end of the cache.
     * If the cache is full, then the item that has not been used for the longest time is deleted.
     * Then the new item is added as the most recently used.
     *
     * @param newElement item to add to cache
     * @param index      unique index for each item in the cache
     */
    public void add(T newElement, int index) {
        if (this.isPresent(index)) {
            this.get(index);
            cache[size].setElement(newElement);
        }
        if (size < capacity - 1) {
            size++;
            cache[size] = new CacheElement<>(newElement, index);
            return;
        } else if (size == capacity - 1) {
            moveElements(0);
            cache[capacity - 1] = new CacheElement<>(newElement, index);
        }
        log.debug("add() - New element {} was added to the cache", newElement);
    }

    /**
     * Removes an item from the cache.
     * Removes voids from the cache by shifting the remainder of the cache to the deleted item.
     * If a request to delete from an empty cache occurs, it simply exits the method.
     *
     * @param element item to remove
     */
    public void delete(T element) {
        if (size == -1) {
            return;
        }
        int numForDelete = -1;
        for (int i = 0; i < capacity; i++) {
            if (cache[i].getElement().equals(element)) {
                numForDelete = i;
                break;
            }
        }
        if (numForDelete >= 0) {
            moveElements(numForDelete);
            size--;
        }
        log.debug("delete() - element {} was deleted from the cache", element);
    }

    /**
     * Determines if element is in the cache.
     *
     * @param element CacheElement to check for presence
     * @return false, if there is no such item in the cache, or an empty cache is accessed.
     * If such an element is found, it returns true.
     */
    public boolean isPresent(CacheElement<T> element) {
        if (size == -1) {
            return false;
        }
        for (int i = 0; i <= size; i++) {
            if (cache[i].equals(element)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determines whether one of the cache entries has an index passed in.
     *
     * @param index
     * @return true или false. If the cache is empty, returns false.
     */
    public boolean isPresent(int index) {
        if (size == -1) {
            return false;
        }
        for (int i = 0; i <= size; i++) {
            if (cache[i] != null && cache[i].getIndex() == index) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves an object from the cache by checking its index field. When an item is found in the cache,
     * marks it as most recently used.
     *
     * @param index
     * @return null if an empty cache was accessed,
     * or an object from the cache with the corresponding index was not found,
     * or found cache object.
     */
    public CacheElement<T> get(int index) {
        CacheElement<T> returnElement = null;
        if (size == -1) {
            return null;
        }
        for (int i = 0; i <= size; i++) {
            if (cache[i].getIndex() == index) {
                returnElement = cache[i];
                moveElements(i);
                cache[size] = returnElement;
                break;
            }
        }
        log.debug("get() - element {} with index {} was returned from the cache", returnElement, index);
        return returnElement;
    }

    /**
     * Clears the cache.
     */
    public void clear() {
        for (int i = 0; i <= size; i++) {
            cache[i] = null;
        }
        size = -1;
        log.debug("clear() - the cache was cleared");
    }

    /**
     * Shifts the items in the cache to the left by one.
     *
     * @param index this element will be erased on shift.
     */
    private void moveElements(int index) {
        for (int i = index; i < capacity - 1; i++) {
            cache[i] = cache[i + 1];
        }
    }
}