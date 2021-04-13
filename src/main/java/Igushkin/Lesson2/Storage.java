package Igushkin.Lesson2;

import lombok.extern.slf4j.Slf4j;

/**
 * Типизированный класс хранилища данных.
 * @param <T>
 */
@Slf4j
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
        this.lastIndex = -1;
        //Изменить уровень логирования в logback.xml на debug при необходимости дебага.
        log.debug("Пустое хранилище создано с размером {}, размер кэша {}", this.storageCapacity, this.CACHE_CAPACITY);
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
        for (int i= 0; i < elements.length; i++) {
            storage[i] = elements[i];
        }
        cache = new Cache<>(CACHE_CAPACITY);
        lastIndex = elements.length - 1;
        //Изменить уровень логирования в logback.xml на debug при необходимости дебага.
        log.debug("Хранилище создано с размером: {}, размер кэша: {}, количество элементов в хранилище: {}",
                this.storageCapacity, this.CACHE_CAPACITY, this.lastIndex + 1);
        log.info("Создание хранилища. Лог должен быть в консоли и в файле.");
    }

    /**
     * Добавляет элемент в хранилище. Если достигнут предел размера хранилища, то емкость хранилища увеличивается в 1,5 раза.
     * @throws MyNullElementException при попытке добавления null в хранилище.
     * @param element элемент для добавления.
     */
    @SuppressWarnings("unchecked")
    public void add (T element) throws MyNullElementException {
        if (element == null) {
            throw new MyNullElementException("Передан null для хранения!");
        }
        if (lastIndex == storageCapacity - 1) {
            expandStorage(FACTOR);
        }
        lastIndex++;
        storage[lastIndex] = element;
        //Изменить уровень логирования в logback.xml на debug при необходимости дебага.
        log.debug("Добавление элемента {}", element.toString());
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
     * @throws NegativeIndexException при попытке удаления из пустого хранилища.
     */
    public void delete () throws NegativeIndexException {
        if (lastIndex < 0) {
            throw new NegativeIndexException("Удаление из пустого хранилища!");
        }
        if (cache.isPresent(lastIndex)) {
            cache.delete(cache.get(lastIndex).getElement());
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
     * @throws NegativeIndexException при попытке получить последний элемент из пустого хранилища.
     * @return Один элемент из хранилища.
     */
    public T getLast() throws NegativeIndexException {
        if (lastIndex < 0) {
            throw new NegativeIndexException("Попытка получить последний элемент из пустого хранилища!");
        }
        return storage[lastIndex];
    }

    /**
     * Получает элемент из хранилища по его индексу внутри хранилища.
     * Сначала проверяет, есть ли такой элемент в кэше. Если есть, то возвращает элемент из кэша, не обращаясь к хранилищу.
     * Если объекта в кэше не оказалось, то добавляет объект в кэш.
     * @throws NegativeIndexException если передается index < 0.
     * @param index номер элемента в хранилище
     * @return null, если было обращение к еще незаполненному элементу хранилища, иначе элемент из хранилища.
     */
    public T get(int index) throws NegativeIndexException {
        if (index < 0) {
            throw new NegativeIndexException("Запрос элемента с отрицательным индексом!");
        }
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