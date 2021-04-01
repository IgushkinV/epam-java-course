package Igushkin.Lesson3;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        HWMethods methods = new HWMethods();
        //1. Заполнить ArrayList
        ArrayList<Human> humanArr = methods.createAndFill();
        //2.Найти дубли и вывести их в консоль.
        methods.findDuplicates(humanArr);
        //3. Удалить дубли из коллекции
        methods.deleteDuplicates(humanArr);
        System.out.println("Осталось элементов " + humanArr.size());
        //4. Отсортировать людей по ФИО
        methods.sortByName(humanArr);
        //5. Отсортировать людей по возрасту
        methods.sortByAge(humanArr);
        //6. Отсортировать людей по адресу (лексикографическая сортировка полного адреса)
        methods.sortByAddress(humanArr);
        methods.print(humanArr);
        //7. Создать класс User с полями ФИО и "роль", которое является перечислением.
        User userAdmin = new User("Nikolay", Role.ADMIN);
        //8. Метод, который принимает объект User и на основании роли пользователя выводит приветствие.
        methods.toGreet(userAdmin);
        //9. Сортировка HashMap по ключу
        HashMap<Integer, String> numMap = new HashMap<>();
        numMap.put(2, "Two");
        numMap.put(1, "One");
        numMap.put(3, "Three");
        numMap.put(5, "Five");
        numMap.put(4, "Four");
        methods.printSortedByKey(numMap);
        System.out.println();
        //10. Сортировка HashMap по значению
        methods.printSortedByValue(numMap);

        // Дополнительно. Демонстрация MultiSortingMap
        System.out.println("*******************");
        MultiSortingMap<Integer, String> multiSortingMap = new MultiSortingMap(SortingMode.BY_KEY);
        multiSortingMap.put(2, "Two");
        multiSortingMap.put(1, "One");
        multiSortingMap.put(3, "Three");
        multiSortingMap.put(5, "Five");
        multiSortingMap.put(4, "Four");
        multiSortingMap.print();
        multiSortingMap.sortByValue();
        multiSortingMap.print();
        System.out.println("The tail: " + multiSortingMap.getTail() + ", режим сортировки - " + multiSortingMap.getSortingMode());
        System.out.println("The newest pair: " + multiSortingMap.getNewest()+ ", режим сортировки - " + multiSortingMap.getSortingMode());
        multiSortingMap.put(0, "Zero");
        System.out.println("The newest pair: " + multiSortingMap.getNewest() + ", режим сортировки - " + multiSortingMap.getSortingMode());
        multiSortingMap.sortByKey();
        multiSortingMap.put(8, "Eight");
        System.out.println("The oldest pair: " + multiSortingMap.getOldest() + ", режим сортировки - " + multiSortingMap.getSortingMode());
        System.out.println("The tail: " + multiSortingMap.getTail() + ", режим сортировки - " + multiSortingMap.getSortingMode());
        multiSortingMap.sortByAddOrder();
        multiSortingMap.print();
        multiSortingMap.delete(2);
        System.out.println(multiSortingMap.getOldest());
    }
}
