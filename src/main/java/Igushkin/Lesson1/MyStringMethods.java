package Igushkin.Lesson1;

import java.util.Locale;

public class MyStringMethods {

    /**
     *Возвращает длину строки.
     */
    public int getLength(String string) {
        return string.length();
    }

    /**
     * Сравнивает 2 строки без учета регистра.
     */
    public boolean equalsWOutCase (String string1, String string2) {
        return string1.toLowerCase(Locale.ROOT).equals(string2.toLowerCase(Locale.ROOT));
    }

    /**
     * Создаёт новую строку с помощью конструктора и заносит ее в пул литералов
     */

    public String getString (String str) {
        return new String(str);
    }

    /**
     * Возвращает массив символов, полученный из строки.
     */

    public char[] getCharArray (String string) {
        return string.toCharArray();
    }

    /**
     * Возвращает массив байтов, полученный из строки.
     */
    public byte[] getByteArray (String string) {
        return string.getBytes();
    }

    /**
     * Приводит строку к верхнему регистру
     */

    public String toUpperCase (String string) {
        return string.toUpperCase();
    }

    /**
     * Находит и возвращает первую позицию символа "а" в строке.
     * Возвращает -1, если символа "а" в строке нет.
     */
     public int FirstAChar (String string) {
         int index = -1;
         char[] chars = string.toCharArray();
         for (int i = 0; i < chars.length; i++) {
             if (chars[i] == 'а') {
                 index = i;
                 break;
             }
         }
         return index;
     }

    /**
     * Находит и возвращает последнюю позицию символа "а" в строке.
     * Возвращает -1, если символа "а" в строке нет.
     */

    public int LastAChar (String string) {
        int index = -1;
        char[] chars = string.toCharArray();
        for (int i = chars.length - 1; i >= 0; i--) {
            if (chars[i] == 'а') {
                index = i;
                break;
            }
        }
        return index;
    }

    /**
     * Проверяет, содержит ли строка слово "Sun".
     * Возвращает true или false.
     */
    public boolean containsSun(String string) {
        return string.contains("Sun");
    }

    /**
     * Проверяет, оканчивается-ли строка на "Oracle".
     * Возвращает true или false.
     */
    public boolean isEndsWithOracle (String string) {
        return string.endsWith("Oracle");
    }

    /**
     * Проверяет, начинается ли строка на "Java".
     * Возвращает true или false.
     */
    public boolean isStarsWithJava (String string) {
        return string.startsWith("Java");
    }

    /**
     * Заменяет все символы "а" в строке на символы "о".
     */
    public String replaceAWithO (String string) {
        return string.replace("а", "о");
    }

    /**
     * Возвращает подстроку с 44 символа по 90 символ.
     */
    public String substringFrom44to90 (String string) {
        if (string.length() < 89) {
            return "Слишком короткая строка! Нужно мимимум 90 символов";
        }
        return string.substring(44, 90);
    }

    /**
     * Разбивает строку по символу пробел (т.е. чтобы каждое слово было отдельным элементом массива).
     * Возвращает массив строк.
     */
     public String[] splitOnSpace(String string) {
         return string.split(" ");
     }

    /**
     * Меняет последовательность символов в строке на обратную.
     */
    public String reverse (String string) {
        StringBuilder builder = new StringBuilder(string);
        return builder.reverse().toString();
    }
}
