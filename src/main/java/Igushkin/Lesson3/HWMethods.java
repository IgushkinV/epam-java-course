package Igushkin.Lesson3;

import java.util.*;

public class HWMethods {

    /**
     * 0. Создать 10 объектов класса Human из которых 2 объекта должны быть дублями (не более одного дубля на каждого).<br>
     * 1. Заполнить ArrayList этими объектами.
     * @return заполненный 10 элементами ArrayList.
     */
    public ArrayList<Human> createAndFill() {
        ArrayList<Human> humans = new ArrayList<>();
        humans.add(new Human("John Smith", 32, new Address("London", "Shaftesbury Avenue", 10, 51)));
        humans.add(new Human("John Smith", 32, new Address("London", "Shaftesbury Avenue", 10, 51)));
        humans.add(new Human("Rowan Sebastian Atkinson", 48, new Address("London", "Baker Street", 38, 153)));
        humans.add(new Human("Rowan Sebastian Atkinson", 48, new Address("London", "Baker Street", 38, 153)));
        humans.add(new Human("Pierre Gasly", 25, new Address("Paris", "Rue du Colonel-Driant", 123, 100)));
        humans.add(new Human("Pierre Gasly", 25, new Address("Paris", "Rue du Colonel-Driant", 123, 100)));
        humans.add(new Human("Lewis Carl Davidson Hamilton", 36, new Address("London", "Oxford Street", 51, 49)));
        humans.add(new Human("Иванов Петр Сидорович", 33, new Address("Moscow", "Сиреневый бульвар", 13, 26)));
        humans.add(new Human("Игушкин Владимир Александрович", 34, new Address("Togliatti", "Цветной бульвар", 10, 51)));
        humans.add(new Human("John Smith", 31, new Address("London", "Piccadilly", 9, 7)));
        return humans;
    }

    /**
     * 2.Найти и распечатать повторяющиеся элементы в коллекции.
     * @param humans коллекция для поиска дубликатов.
     */
    public void findDuplicates(ArrayList<Human> humans) {
        System.out.println("Дублированные элементы в переданном ArrayList: ");
        for (int i = 0; i < humans.size(); i++) {
            Human humForComparison = humans.get(i);
            for (int j = i + 1; j < humans.size(); j++) {
                if (humForComparison.equals(humans.get(j))) {
                    System.out.println(humForComparison);
                    break;
                }
            }
        }
    }

    /**
     * 3. Удалить дубли из коллекции. Должно остаться 7 объектов.
     * @param humans коллекция для удаления дублей.
     */
    public void deleteDuplicates(ArrayList<Human> humans) {
        Set<Human> humanSet = new HashSet<>(humans);
        humans.clear();
        humans.addAll(humanSet);
    }

    /**
     * 4. Отсортировать людей по ФИО
     * @param humans коллекция для сортировки
     */
    public void sortByName (ArrayList<Human> humans) {
        Comparator<Human> compareByName = new Comparator<Human>() {
            @Override
            public int compare(Human human, Human t1) {
                return human.getName().compareTo(t1.getName());
            }
        };
        Collections.sort(humans, compareByName);
    }

    /**
     * 5. Отсортировать людей по возрасту.
     * @param humans колекция для сортировки
     */
    public void sortByAge (ArrayList<Human> humans) {
        Comparator<Human> compareByAge = new Comparator<Human>() {
            @Override
            public int compare(Human human, Human t1) {
                return Integer.compare(human.getAge(), t1.getAge());
            }
        };
        Collections.sort(humans, compareByAge);
    }

    /**
     * 6. Отсортировать людей по адресу (лексикографическая сортировка полного адреса).
     * @param humans коллекция для сортировки
     */
    public void sortByAddress (ArrayList<Human> humans) {
        Comparator<Human> compareByAddress = new Comparator<Human>() {
            @Override
            public int compare(Human human, Human t1) {
                return human.getAddress().compareTo(t1.getAddress());
            }
        };
        Collections.sort(humans, compareByAddress);
    }

    /**
     * 8. На основании роли пользователя выводит приветственное сообщение в виде "Приветствуем пользователя name с правами" + описание роли.
     * @param user пользователь, у которого берется имя и роль.
     */
    public void toGreet(User user) {
        System.out.println("Приветствуем пользователя " + user.getName() + " c правами " + user.getGreeting(user.getRole()));
    }

    public void printSortedByKey(HashMap<Integer, String> map) {
        TreeMap<Integer, String> sortedMap = new TreeMap<>(map);
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            System.out.printf("%d %s%n", entry.getKey(), entry.getValue());
        }
    }
    public void printSortedByValue(HashMap<Integer, String> map) {
        TreeSet<String> valueSet = new TreeSet<>();
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            valueSet.add(entry.getValue());
        }
        for (String str : valueSet) {
            for (Map.Entry<Integer, String> entry : map.entrySet()) {
                if (str.equals(entry.getValue())) {
                    System.out.println(entry.getKey() + " " + entry.getValue());
                }
            }
        }
    }
    public void print (ArrayList<Human> humans) {
        for (Human human : humans) {
            System.out.println(human);
        }
    }
}

