package igushkin.lesson2;

import igushkin.lesson2.exceptions.MyNullElementException;
import igushkin.lesson2.exceptions.NegativeIndexException;
import lombok.extern.slf4j.Slf4j;

/**
 * Stores data. Generic class.
 *
 * @param <T>
 */
@Slf4j
public class Storage<T> {

    private final int cacheCapacity = 10;
    private final double factor = 1.5;

    private T[] storage;
    private Cache<T> cache;
    private int capacity = 10;
    /**
     * Field. Contains the index of the last (not null) stored item. Equal to -1 if the storage is empty.
     */
    private int size = -1;

    /**
     * Constructor. Creates an empty store and a cache for it.
     */
    @SuppressWarnings("unchecked")
    public Storage() {
        this.storage = (T[]) new Object[capacity];
        this.cache = new Cache<>(cacheCapacity);
        this.size = -1;
        log.debug("Empty storage created with capacity {}, cache capacity {}", this.capacity, this.cacheCapacity);
    }

    /**
     * Constructor. Creates a data store from the passed array.
     * Creates a cache of the size of the passed array and default capacity.
     *
     * @param elements generic array.
     */
    @SuppressWarnings("unchecked")
    public Storage(T[] elements) {
        if (elements.length > this.capacity) {
            this.capacity = elements.length;
        }
        this.storage = (T[]) new Object[capacity];
        System.arraycopy(elements, 0, storage, 0, elements.length);
        cache = new Cache<>(cacheCapacity);
        size = elements.length - 1;
        log.debug("The storage was created with capacity: {}, cache capacity: {}, number of items in the storage: {}",
                this.capacity, this.cacheCapacity, this.size + 1);
    }

    /**
     * Adds an item to the store. If the storage size limit is reached,
     * then the storage capacity is increased by 1.5 times.
     *
     * @param element item to add.
     * @throws MyNullElementException when trying to add null to the store.
     */
    public void add(T element) throws MyNullElementException {
        if (element == null) {
            throw new MyNullElementException("add() - Passed null for storage!");
        }
        if (size == capacity - 1) {
            expandStorage(factor);
        }
        size++;
        storage[size] = element;
        log.debug("add() - Adding the item {}", element.toString());
    }

    /**
     * Removes the item that was last added to the store. Before deleting, checks if such an item is in the cache.
     * If so, it first deletes it from the cache, then from the storage.
     *
     * @throws MyNullElementException when trying to delete from empty storage.
     */
    public void delete() throws MyNullElementException {
        if (isStorageEmpty()) {
            throw new MyNullElementException("Trying to delete from empty storage!");
        }
        if (cache.isPresent(size)) {
            cache.delete(cache.get(size).getElement());
        }
        storage[size] = null;
        size--;
        log.debug("delete() - last element was deleted from store");
    }

    /**
     * Clears the cache. Clears the storage.
     */
    public void clear() {
        cache.clear();
        for (int i = 0; i <= size; i++) {
            storage[i] = null;
        }
        size = -1;
        log.debug("clear() - the store and the cache was cleared");
    }

    /**
     * Gets the last item added to the store.
     *
     * @return T element from the store
     * @throws MyNullElementException when trying to get the last item from an empty store.
     */
    public T getLast() throws MyNullElementException {
        if (isStorageEmpty()) {
            throw new MyNullElementException("Trying to get the last item from the empty store!");
        }
        log.debug("getLast() - the last element was returned");
        return storage[size];
    }

    /**
     * Gets an item from the store by its index within the store.
     * First checks if there is such an item in the cache.
     * If the object is not in the cache, then it adds the object to the cache.
     *
     * @param index item number in the store
     * @return null, if there was a call to a still unfilled storage item, otherwise an item from the storage.
     * @throws NegativeIndexException if index <0 is passed.
     */
    public T get(int index) throws NegativeIndexException, IndexOutOfBoundsException, MyNullElementException {
        validateIndex(index);
        if (isStorageEmpty()) {
            throw new MyNullElementException("Trying to get item from the empty store!");
        }
        if (cache.isPresent(index)) {
            return cache.get(index).getElement();
        }
        cache.add(storage[index], index);
        T returnedElement = storage[index];
        log.debug("get() - element {} was returned from the store", returnedElement);
        return returnedElement;
    }

    @SuppressWarnings("unchecked")
    private void expandStorage(double factor) {
        int newCapacity = (int) Math.round(capacity * factor);
        log.debug("expandStorage() - The store was expanded. Old capacity = {}, new capacity = {}",
                this.capacity, newCapacity);
        this.capacity = newCapacity;
        T[] newStorage = (T[]) new Object[capacity];
        System.arraycopy(storage, 0, newStorage, 0, storage.length);
        storage = newStorage;
    }

    private boolean isStorageEmpty() {
        return size < 0;
    }

    private void validateIndex(int index) throws NegativeIndexException, IndexOutOfBoundsException, MyNullElementException {
        if (index < 0) {
            throw new NegativeIndexException("Requesting an item with a negative index!");
        } else if (index > this.capacity) {
            throw new IndexOutOfBoundsException("Request for an item whose index exceeds the storage capacity!");
        }
    }
}