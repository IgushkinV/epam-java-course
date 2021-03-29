package Igushkin.Lesson2;

/**
 * Типизированный класс хранилища данных.
 * @param <T>
 */
public class Storage<T> {

    private T[] storage;
    private Cache<T> cache;
    private final int CACHE_CAPACITY = 10;
    private int storageCapacity = 10;
    private int indexOfMostRightNotNullStorageElem = -1;

    /**
     * Конструктор. Создает пустое хранилище и кэш для него.
     */
    @SuppressWarnings("unchecked")
    public Storage() {
        this.storage = (T[]) new Object[storageCapacity];
        this.cache = new Cache<T>(CACHE_CAPACITY);
    }

    /**
     * Конструктор. Создает хранилище данных из переданного массива. Создает кэш размером с переданный массив.
     * @param elements типизированный массив.
     */
    @SuppressWarnings("unchecked")
    public Storage(T[] elements) {
        if (elements.length > this.storageCapacity) {
            this.storageCapacity = elements.length;
        }
        this.storage = (T[]) new Object[storageCapacity];
        for (int i= 0; i < storageCapacity; i++) {
            this.storage[i] = elements[i];
        }
        this.cache = new Cache<>(CACHE_CAPACITY);
        this.indexOfMostRightNotNullStorageElem = storageCapacity - 1;
    }

    /**
     * Добавляет элемент в хранилище. Если достигнут предел размера хранилища, то емкость хранилища увеличивается в 1,5 раза.
     * @param element элемент для добавления.
     */
    @SuppressWarnings("unchecked")
    public void add (T element) {
        if (indexOfMostRightNotNullStorageElem == storageCapacity - 1) {
            this.storageCapacity = (int) Math.round(storageCapacity * 1.5);
            T[] newStorage = (T[]) new Object[storageCapacity];
            for (int i = 0; i < storage.length; i++) {
                newStorage[i] = storage[i];
            }
            storage = newStorage;
        }
        indexOfMostRightNotNullStorageElem++;
        storage[indexOfMostRightNotNullStorageElem] = element;
    }

    /**
     * Удаляет элемент, который был последним добавлен в хранилище. Перед удалением проверяет, есть ли такой элемент в кэше.
     * Если есть, то сначала удаляет из кэша, потом из хранилища.
     */
    public void delete () {
        if (cache.isPresent(indexOfMostRightNotNullStorageElem)) {
            cache.delete(cache.get(indexOfMostRightNotNullStorageElem));
        }
        storage[indexOfMostRightNotNullStorageElem] = null;
        indexOfMostRightNotNullStorageElem--;
    }

    /**
     * Очищает кэш.
     */
    public void clear() {
        cache.clear();
    }

    /**
     * Получает элемент, последним добавленный в хранилище.
     * @return Один элемент из хранилища.
     */
    public T getLast() {
        return storage[indexOfMostRightNotNullStorageElem];
    }

    /**
     * Получает элемент из хранилища по его индексу внутри хранилища.
     * Сначала проверяет, есть ли такой элемент в кэше. Если есть, то возвращает элемент из кэша, не обращаясь к хранилищу.
     * Если объекта в кэше не оказалось, то добавляет объект в кэш.
     * @param index номер элемента в хранилище
     * @return null, если было обращение к еще незаполненному элементу хранилища, иначе элемент из хранилища.
     */
    public T get(int index) {
        if (index > indexOfMostRightNotNullStorageElem) {
            return null;
        }
        if (cache.isPresent(index)) {
            return cache.get(index).element;
        }
        cache.add(new CacheElement<>(storage[index]), index);
        return storage[index];
    }
}