package Igushkin.Lesson4;

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
        stringStorage.add("Three");
        stringStorage.add("AnotherOne");
        try {
            System.out.println(stringStorage.get(3));
        } catch (NegativeIndexException e) {
            e.printStackTrace();
        }
        stringStorage.clear();
        Storage<Integer> oneElemStorage = new Storage<>(new Integer[]{-1});
        //Удаление единственного элемента из хранилища. Теперь оно пустое.
        try {
            oneElemStorage.delete();
        } catch (NegativeIndexException e) {
            e.printStackTrace();
        }
        //Попытка получить элемент из пустого хранилища выбрасывает проверяемое исключение.
        try {
            oneElemStorage.getLast();
        } catch (NegativeIndexException e) {
            e.printStackTrace();
        }
        //Удаление из пустого хранилища выбрасывает проверяемое исключение.
        try {
            oneElemStorage.delete();
        } catch (NegativeIndexException e) {
            e.printStackTrace();
        }

        Storage<String> nameStorage = new Storage<>(new String[]{"Sara", "Ken", "Ada", "Bob", "Charly"});
        nameStorage.add("Elena");
        try {
            System.out.println(nameStorage.getLast());
        } catch (NegativeIndexException e) {
            e.printStackTrace();
        }

        Storage<Integer> numStorage = new Storage<>(new Integer[]{1,2,3,4,5});
        try {
            System.out.println(numStorage.getLast() + numStorage.get(0));
        } catch (NegativeIndexException e) {
            e.printStackTrace();
        }
        log.debug("Первое сообщение лог. Уровень дебаг.");
        //Метод, выбрасывающий проверяемое исключение. Оно обработано, программа выполняется дальше.
        try {
            nameStorage.get(-1);
        } catch (NegativeIndexException e) {
            e.printStackTrace();
            log.warn("Запрос элемента с отрицательным индексом");
        }
        //Метод, выбрасывающий непроверяемое исключение. Программа не выполняется дальше.
        nameStorage.add(null);
        //Этот блок кода не выполнится.
        try {
            System.out.println(nameStorage.getLast());
        } catch (NegativeIndexException e) {
            e.printStackTrace();
        }
    }
}
