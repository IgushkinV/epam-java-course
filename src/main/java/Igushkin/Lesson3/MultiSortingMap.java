package Igushkin.Lesson3;

import java.util.*;

/**
 * Класс для хранения коллекции Map<K,V>. Позволяет сортировать коллецию по ключу, по значению,
 * добавлять и удалять элементы, получать первый и последний элементы в зависимости от режима сортировки,
 * самый старый и самый новый элементы.
 * @param <K> ключ
 * @param <V> значение
 */
public class MultiSortingMap<K, V>{

    private LinkedHashMap<K,V> originalMap;
    private LinkedHashMap<K,V> valueSortedMap;
    private LinkedHashMap<K, V> keySortedMap;
    private SortingMode sortingMode;

    public MultiSortingMap() {
        this.originalMap = new LinkedHashMap<>();
        this.sortingMode = SortingMode.BY_ADD_ORDER;
    }

    public MultiSortingMap(SortingMode sortingMode) {
        this.sortingMode = sortingMode;
        this.originalMap = new LinkedHashMap<>();
    }

    public MultiSortingMap(Map<K,V> map, SortingMode sortingMode) {
        this.originalMap = new LinkedHashMap<>(map);
        this.sortingMode = sortingMode;
            if (sortingMode == SortingMode.BY_KEY) {
                this.sortByKey();
            } else if (sortingMode == SortingMode.BY_VALUE) {
                this.sortByValue();
        }
    }

    public SortingMode getSortingMode() {
        return sortingMode;
    }

    /**
     * Сортировка по порядку добавления.
     * @return
     */
    public void sortByAddOrder () {
        sortingMode = SortingMode.BY_ADD_ORDER;
    }

    /**
     * Сортировка по ключу.
     * @return  LinkedHashMap<K,V> 
     */
    public void sortByKey () {
        sortingMode = SortingMode.BY_KEY;
        TreeMap<K, V> treeMap = new TreeMap<>(this.originalMap);
        keySortedMap = new LinkedHashMap<>(treeMap);
    }

    /**
     * Сортировка по значению.
     * @return  LinkedHashMap<K,V>
     */
    public void sortByValue () {
        valueSortedMap = new LinkedHashMap<>();
        sortingMode = SortingMode.BY_VALUE;
        Set<V> values = new TreeSet<>(this.originalMap.values());
        for (V value : values) {
            for (Map.Entry<K,V> entry : this.originalMap.entrySet()) {
                if (value.equals(entry.getValue())) {
                    valueSortedMap.put(entry.getKey(), value);
                }
            }
        }
    }

    /**
     * Associates the specified value with the specified key in this map. If the map previously contained a mapping for the key, the old value is replaced.
     * @param k
     * @param v
     */
    public void put (K k, V v) {
        this.originalMap.put(k, v);
        switch (sortingMode) {
            case BY_KEY:
                this.sortByKey();
                break;
            case BY_VALUE:
                this.sortByValue();
                break;
            case BY_ADD_ORDER:
                break;
            default:
        }
    }

    /**
     * Возвращает значение, ассоциированное с ключом k.
     * @param k
     * @return V value
     */
    public V get (K k) {
        return this.originalMap.get(k);
    }

    /**
     * Возвращает пару ключ-значение, которая при текущем режиме сортировки находится в конце коллекции.
     * @return Map.Entry
     */
    public Map.Entry<K,V> getTail() {
        Map.Entry<K,V> tailEntry = null;
        LinkedHashMap<K, V> thisMap = null;
            if (sortingMode == SortingMode.BY_KEY) {
                thisMap = this.keySortedMap;
            } else if (sortingMode == SortingMode.BY_VALUE) {
                thisMap = this.valueSortedMap;
            } else if (sortingMode == SortingMode.BY_ADD_ORDER) {
                thisMap = this.originalMap;
            }
            for (Map.Entry<K,V> entry : thisMap.entrySet()) {
                tailEntry = entry;
            }
        return tailEntry;
    }

    /**
     * Возвращает пару ключ-значение, которая при текущем режиме сортировки находится в начале коллекции.
     * @return Map.Entry
     */
    public Map.Entry<K,V> getHead() {
        LinkedHashMap<K, V> thisMap = null;
        if (sortingMode == SortingMode.BY_KEY) {
            thisMap = this.keySortedMap;
        } else if (sortingMode == SortingMode.BY_VALUE) {
            thisMap = this.valueSortedMap;
        } else if (sortingMode == SortingMode.BY_ADD_ORDER) {
            thisMap = this.originalMap;
        }
        return thisMap.entrySet().iterator().next();
    }

    /**
     * Поиск пары ключ-значение, которая была добавлена в коллецию первой (раньше остальных). Не зависит от режима сортировки коллекции.
     * @return Map.Entry<K,V>
     */
    public Map.Entry<K, V> getOldest() {
        return this.originalMap.entrySet().iterator().next();
    }

    /**
     * Поиск пары ключ-значение, которая была добавлена в коллецию последней. Не зависит от режима сортировки коллекции.
     * @return Map.Entry<K,V>
     */
    public Map.Entry<K, V> getNewest() {
        Map.Entry<K,V> newest = null;
        for (Map.Entry<K,V> entry : this.originalMap.entrySet()) {
            newest = entry;
        }
        return newest;
    }

    /**
     * Распечатывает коллекцию, указывая текущий режим сотрировки.
     */
    public void print() {
        LinkedHashMap<K,V> thisMap;
        if (sortingMode == SortingMode.BY_KEY) {
            thisMap = keySortedMap;
        } else if (sortingMode == SortingMode.BY_VALUE) {
            thisMap = valueSortedMap;
        } else {
            thisMap = originalMap;
        }
        System.out.println("Режим сортировки: " + this.sortingMode);
        thisMap.entrySet().forEach(System.out::println);
    }

    /**
     * Удаляет ключ key из текущей коллекции.
     * @param key
     */
    public void delete (K key) {
        originalMap.remove(key);
        if (sortingMode == SortingMode.BY_KEY) {
            this.sortByKey();
        } else if (sortingMode == SortingMode.BY_VALUE) {
            originalMap.remove(key);
            this.sortByValue();
        }
    }
}
