package igushkin.lesson2;

import igushkin.lesson2.exceptions.MyNullElementException;
import lombok.extern.slf4j.Slf4j;

/**
 * Показывает примеры использования типизированного класса Storage<T> и его методов.
 */
@Slf4j
public class Main {

    public static void main(String[] args) {
        Storage<String> stringStorage = new Storage<>();
        stringStorage.add("One");
        stringStorage.add("Two");
        stringStorage.add("AnotherOne");
        log.info("Элемент хранилища c индексом 2: {}", stringStorage.get(2));
        try {
            stringStorage.get(15);
        } catch (IndexOutOfBoundsException e) {
            log.warn("Запрошен элемент из-за границ хранилища.", e);
        }
        stringStorage.clear();

        Storage<Integer> oneElemStorage = new Storage<>(new Integer[]{-1});
        //Удаление единственного элемента из хранилища. Теперь оно пустое.
        oneElemStorage.delete();
        //Попытка получить элемент из пустого хранилища выбрасывает непроверяемое исключение.
        try {
            oneElemStorage.getLast();
        } catch (MyNullElementException e) {
            log.warn("Запрос на получение из пустого хранилища.", e);
        }
        //Удаление из пустого хранилища выбрасывает непроверяемое исключение исключение.
        try {
            oneElemStorage.delete();
        } catch (MyNullElementException e) {
            log.warn("Запрос на удаление из пустого хранилища.", e);
        }

        Storage<String> nameStorage = new Storage<>(new String[]{"Sara", "Ken", "Ada", "Bob", "Charly"});
        nameStorage.add("Elena");
        try {
            log.info("Последний добавленный элемент: {}", nameStorage.getLast());
        } catch (MyNullElementException e) {
            log.warn("Запрос последнего элемента из пустого хранилища.", e);
        }
    }
}
