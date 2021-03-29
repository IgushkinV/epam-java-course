package Igushkin.Lesson2;

/**
 * Типизированный класс для создания кэша. Не содержит одинаковых элементов.
 * @param <T>
 */
public class Cache<T> {

    private int capacityOfCache;
    private CacheElement[] cache;
    private int indexOfMostRightNotNullCacheElement;

    /**
     * Конструктор. Создает объект кэша с заданным размером.
     * @param capacityOfCache
     */
    public Cache(int capacityOfCache) {
        this.capacityOfCache = capacityOfCache;
        this.cache = new CacheElement[capacityOfCache];
        this.indexOfMostRightNotNullCacheElement = -1;
    }

    /**
     * Добавляет element в кэш. Проверяет на повторения, не добавляет повторяющиеся элементы.
     * При попытке добавить элемент повторно отметит его как самый недавно используемый.
     * Добавление происходит в конец кэша (в первый not-null елемент массива).
     * Если кэш заполнен, то удаляется элемент, дольше остальных не используемый,
     * затем новый элемент добавляется как самый недавно используемый.
     * @param elementToAdd элемент для добавления в кэш
     * @param index уникальный для каждого элемента в кэше индекс
     */
    public void add(CacheElement<T> elementToAdd, int index) {
        if (this.isPresent(index)) {
            this.get(index); //переставляет элемент последним в кэше
            cache[indexOfMostRightNotNullCacheElement].element = elementToAdd.element;

        }
        if (indexOfMostRightNotNullCacheElement < capacityOfCache - 1) { //Если кэш не заполнен полностью (либо пустой)
            indexOfMostRightNotNullCacheElement++;
            cache[indexOfMostRightNotNullCacheElement] = new CacheElement<>(elementToAdd.element);
            cache[indexOfMostRightNotNullCacheElement].index = index;
            return;
        }
        if (indexOfMostRightNotNullCacheElement == capacityOfCache - 1) { //Если кэш заполнен полностью
            for (int i = 0; i < capacityOfCache - 1; i++) {
                cache[i] = cache[i + 1];
            }
            cache[capacityOfCache - 1] = new CacheElement<>(elementToAdd.element);
            cache[capacityOfCache - 1].index = index;
        }
    }

    /**
     * Удалаяет элемент из кэша. Удаляет пустоты из кэша, сдвигая оставшуюся часть кэша на удаленный элемент.
     * Если происходит запрос на удаление из пустого кэша, то просто выходит из метода.
     * @param element элемент для удаления
     */
    public void delete(CacheElement<T> element) {
        if (indexOfMostRightNotNullCacheElement == -1) {
            return;
        }
        int numForDelete = -1;
        for (int i = 0; i < capacityOfCache; i++) {
            if (cache[i].equals(element)) {
                numForDelete = i;
                break;
            }
        }
        if (numForDelete >= 0) {
            for (int i = numForDelete; i < capacityOfCache - 1; i++) {
                cache[i] = cache[i + 1];
            }
            indexOfMostRightNotNullCacheElement--;
        }
    }

    /**
     * Определяет, есть ли element в кэше.
     * @param element
     * @return false, если такого элемента нет в кэше, либо происходит обращение к пустому кэшу.
     * Если такой элемент найден, возвращает true.
     */
    public boolean isPresent(CacheElement<T> element) {
        if (indexOfMostRightNotNullCacheElement == -1) {
            return false;
        }
        for (int i = 0; i <= indexOfMostRightNotNullCacheElement; i++) {
            if (cache[i].equals(element)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Определяет, имеет ли один из элементов кэша переданный индекс.
     * @param index
     * @return true или false
     */
    public boolean isPresent(int index) {
        if (indexOfMostRightNotNullCacheElement == -1) {
            return false;
        }
        for (int i = 0; i <= indexOfMostRightNotNullCacheElement; i++) {
            if (cache[i] != null) {
                if (cache[i].index == index) {
                    return true;
                }
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
        if (indexOfMostRightNotNullCacheElement == -1) {
            return null;
        }
        for (int i = 0; i <= indexOfMostRightNotNullCacheElement; i++) {
            if (cache[i].index == index) {
                returnElement = cache[i];
                for (int j = i; j <= indexOfMostRightNotNullCacheElement - 1; j++) {
                    cache[j] = cache[j + 1];
                }
                cache[indexOfMostRightNotNullCacheElement] = returnElement;
                break;
            }
        }
        return returnElement;
    }

    /**
     * Очищает весь кэш.
     */
    public void clear() {
       for (int i = 0; i <= indexOfMostRightNotNullCacheElement; i++) {
           cache[i] = null;
       }
       indexOfMostRightNotNullCacheElement = -1;
    }
}