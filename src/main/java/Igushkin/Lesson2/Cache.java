package Igushkin.Lesson2;

import lombok.extern.slf4j.Slf4j;

/**
 * Типизированный класс для создания кэша. Не содержит одинаковых элементов.
 * @param <T>
 */
@Slf4j
public class Cache<T> {

    private int capacityOfCache;
    private CacheElement<T>[] cache;
    /**Поле. Содержит индекс последнего ненулевого элемента в кэше. Если кэш пустой, равно -1. */
    private int lastIndex;

    /**
     * Конструктор. Создает объект кэша с заданным размером.
     * @param capacityOfCache размер кэша.
     */
    @SuppressWarnings("unchecked")
    public Cache(int capacityOfCache) {
        this.capacityOfCache = capacityOfCache;
        this.cache = new CacheElement[capacityOfCache];
        this.lastIndex = -1;
        log.info("Создание кэша. Должно быть только в файле.");
    }

    /**
     * Добавляет element в кэш. Проверяет на повторения, не добавляет повторяющиеся элементы.
     * При попытке добавить элемент повторно отметит его как самый недавно используемый.
     * Добавление происходит в конец кэша (в первый not-null елемент массива).
     * Если кэш заполнен, то удаляется элемент, дольше остальных не используемый,
     * затем новый элемент добавляется как самый недавно используемый.
     * @param newElement элемент для добавления в кэш
     * @param index уникальный для каждого элемента в кэше индекс
     */
    public void add(T newElement, int index) {
        if (this.isPresent(index)) {
            this.get(index); //переставляет элемент последним в кэше
            cache[lastIndex].setElement(newElement);
        }
        if (lastIndex < capacityOfCache - 1) { //Если кэш не заполнен полностью (либо пустой)
            lastIndex++;
            cache[lastIndex] = new CacheElement<>(newElement, index);
            return;
        }
        if (lastIndex == capacityOfCache - 1) { //Если кэш заполнен полностью
            moveElements(0);
            cache[capacityOfCache - 1] = new CacheElement<>(newElement,index);
        }
    }

    /**
     * Сдвигает элементы кэша на один влево.
     * @param index этот элемент будет стерт при сдвиге.
     */
    private void moveElements(int index) {
        for (int i = index; i < capacityOfCache - 1; i++) {
            cache[i] = cache[i + 1];
        }
    }

    /**
     * Удаляет элемент из кэша. Удаляет пустоты из кэша, сдвигая оставшуюся часть кэша на удаленный элемент.
     * Если происходит запрос на удаление из пустого кэша, то просто выходит из метода.
     * @param element элемент для удаления
     */
    public void delete(T element) {
        if (lastIndex == -1) {
            return;
        }
        int numForDelete = -1;
        for (int i = 0; i < capacityOfCache; i++) {
            if (cache[i].getElement().equals(element)) {
                numForDelete = i;
                break;
            }
        }
        if (numForDelete >= 0) {
            moveElements(numForDelete);
            lastIndex--;
        }
    }

    /**
     * Определяет, есть ли element в кэше.
     * @param element
     * @return false, если такого элемента нет в кэше, либо происходит обращение к пустому кэшу.
     * Если такой элемент найден, возвращает true.
     */
    public boolean isPresent(CacheElement<T> element) {
        if (lastIndex == -1) {
            return false;
        }
        for (int i = 0; i <= lastIndex; i++) {
            if (cache[i].equals(element)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Определяет, имеет ли один из элементов кэша переданный индекс.
     * @param index
     * @return true или false. Если кэш пустой, возвращает false.
     */
    public boolean isPresent(int index) {
        if (lastIndex == -1) {
            return false;
        }
        for (int i = 0; i <= lastIndex; i++) {
            if (cache[i] != null && cache[i].getIndex() == index) {
                return true;
            }
        }
        return false;
    }

    /**
     * Получает объект из кэша, проверяя его поле index. При нахождении элемента в кэше помечает его как самый недавно использованный.
     * @param   index
     * @return  null, если произошло обращение к пустому кэшу, или объект из кэша с соответствующим индексом не найден,
     *          либо найденный объект кэша.
     */
    public CacheElement<T> get(int index) {
        CacheElement<T> returnElement = null;
        if (lastIndex == -1) {
            return null;
        }
        for (int i = 0; i <= lastIndex; i++) {
            if (cache[i].getIndex() == index) {
                returnElement = cache[i];
                for (int j = i; j <= lastIndex - 1; j++) {
                    cache[j] = cache[j + 1];
                }
                cache[lastIndex] = returnElement;
                break;
            }
        }
        return returnElement;
    }

    /**
     * Очищает весь кэш.
     */
    public void clear() {
        for (int i = 0; i <= lastIndex; i++) {
            cache[i] = null;
        }
        lastIndex = -1;
    }
}