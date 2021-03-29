package Igushkin.Lesson2;

/**
 * Показывает примеры использования типизированного класса Storage<T> и его методов.
 */
public class Main {

    public static void main(String[] args) {
        Storage<String> stringStorage = new Storage<>();
        stringStorage.add("One");
        stringStorage.add("Two");
        stringStorage.add("Three");
        stringStorage.add("AnotherOne");
        System.out.println(stringStorage.get(3));
        System.out.println(stringStorage.getLast());
        stringStorage.delete();
        System.out.println(stringStorage.getLast());
        stringStorage.clear();

        Storage<String> nameStorage = new Storage<>(new String[]{"Sara", "Ken", "Ada", "Bob", "Charly"});
        System.out.println(nameStorage.getLast());
        nameStorage.add("Elena");
        System.out.println(nameStorage.getLast());
        System.out.println(nameStorage.get(1));

        Storage<Integer> numStorage = new Storage<>(new Integer[]{1,2,3,4,5});
        System.out.println(numStorage.get(3));
        System.out.println(numStorage.getLast() + numStorage.get(0));
    }
}
