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
    private final double FACTOR = 1.5;
    /**Поле. Содержит индекс последнего заполненного (not null) элемента хранилища. Равно -1, если хранилище пустое.*/
    private int lastIndex = -1;

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
        this.lastIndex = storageCapacity - 1;
    }

    /**
     * Добавляет элемент в хранилище. Если достигнут предел размера хранилища, то емкость хранилища увеличивается в 1,5 раза.
     * @param element элемент для добавления.
     */
    @SuppressWarnings("unchecked")
    public void add (T element) {
        if (lastIndex == storageCapacity - 1) {
            expandStorage(FACTOR);
        }
        lastIndex++;
        storage[lastIndex] = element;
    }

    private void expandStorage(double factor) {
        this.storageCapacity = (int) Math.round(storageCapacity * factor);
        T[] newStorage = (T[]) new Object[storageCapacity];
        for (int i = 0; i < storage.length; i++) {
            newStorage[i] = storage[i];
        }
        storage = newStorage;
    }

    /**
     * Удаляет элемент, который был последним добавлен в хранилище. Перед удалением проверяет, есть ли такой элемент в кэше.
     * Если есть, то сначала удаляет из кэша, потом из хранилища.
     */
    public void delete () {
        if (cache.isPresent(lastIndex)) {
            cache.delete(cache.get(lastIndex));
        }
        storage[lastIndex] = null;
        lastIndex--;
    }

    /**
     * Очищает кэш. Очищает хранилище.
     */
    public void clear() {
        cache.clear();
        for (int i = 0; i <= lastIndex; i++) {
            storage[i] = null;
        }
        lastIndex = -1;
    }

    /**
     * Получает элемент, последним добавленный в хранилище.
     * @return Один элемент из хранилища.
     */
    public T getLast() {
        return storage[lastIndex];
    }

    /**
     * Получает элемент из хранилища по его индексу внутри хранилища.
     * Сначала проверяет, есть ли такой элемент в кэше. Если есть, то возвращает элемент из кэша, не обращаясь к хранилищу.
     * Если объекта в кэше не оказалось, то добавляет объект в кэш.
     * @param index номер элемента в хранилище
     * @return null, если было обращение к еще незаполненному элементу хранилища, иначе элемент из хранилища.
     */
    public T get(int index) {
        if (index > lastIndex) {
            return null;
        }
        if (cache.isPresent(index)) {
            return cache.get(index).getElement();
        }
        cache.add(storage[index], index);
        return storage[index];
    }
}