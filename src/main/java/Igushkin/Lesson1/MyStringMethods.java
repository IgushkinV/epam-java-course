package Igushkin.Lesson1;

import java.util.Locale;

/**
 * Содержит в себе нестатические методы для работы со строками. Большинство методов представляют собой
 * конкретную реализацию аналогичного метода из класса {@link java.lang.String String}.
 */
public class MyStringMethods {

    /**
     *Возвращает длину строки.
     *
     * @param   string  строка, длину которой нужно найти
     * @return  целое число.
     */
    public int getLength(String string) {
        return string.length();
    }

    /**
     * Сравнивает 2 строки без учета регистра.
     *
     * @param   string1 Первый строковый литерал для сравнения.
     * @param   string2 Второй строковый литерал для сравнения.
     * @return  {@code true}, если первая строка равна второй без учета регистра, иначе {@code false}.
     */
    public boolean equalsWOutCase (String string1, String string2) {
        return string1.toLowerCase(Locale.ROOT).equals(string2.toLowerCase(Locale.ROOT));
    }

    /**
     * Создаёт новый объект типа {@code String} с помощью конструктора и заносит его в пул литералов.
     *
     * @param   str текст для создания нового объекта {@code String}.
     * @return  новый объект класса {@code String}.
     */
    public String getString (String str) {
        return new String(str);
    }

    /**
     * Возвращает массив символов, полученный из строки.
     *
     * @param   string  строка, которая будет разбита на массив символов.
     * @return  массив символов.
     */
    public char[] getCharArray (String string) {
        return string.toCharArray();
    }

    /**
     * Возвращает массив байтов, предаставляющих собой коды символов, составляющих строку {@code string}.
     * @param   string    строка, которая будет преобразована в массив байтов.
     * @return  массив байтов.
     */
    public byte[] getByteArray (String string) {
        return string.getBytes();
    }

    /**
     * Приводит строку к верхнему регистру
     *
     * @param   string  строка, буквы которой нужно сделать строчными.
     * @return  строку строчных букв.
     */
    public String toUpperCase (String string) {
        return string.toUpperCase();
    }

    /**
     * Находит и возвращает первую позицию символа "а" в строке.
     *
     * @param   string строка для поиска символа "а".
     * @return  номер позиции первого встреченного символа "а" или -1, если символа "а" в строке нет.
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
     *
     * @param   string   строка для поиска символа "а".
     * @return  номер позиции последнего символа "а" или -1, если символа "а" в строке нет.
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
     *
     * @param   string  строка для поиска подстроки "Sun".
     * @return {@code true} если находит такую подстроку, иначе {@code false}.
     */
    public boolean containsSun(String string) {
        return string.contains("Sun");
    }

    /**
     * Проверяет, оканчивается-ли строка на "Oracle".
     *
     * @param   string  строка для проверки ее на окончание "Oracle".
     * @return {@code true} если переданная строка заканивается на "Oracle", иначе {@code false}.
     */
    public boolean isEndsWithOracle (String string) {
        return string.endsWith("Oracle");
    }

    /**
     * Проверяет, начинается ли строка на "Java".
     *
     * @param   string строка для проверки ее на начало "Java".
     * @return {@code true} если переданная строка начинается на "Java", иначе {@code false}.
     */
    public boolean isStarsWithJava (String string) {
        return string.startsWith("Java");
    }

    /**
     * Заменяет все символы "а" в строке на символы "о".
     *
     * @param   string  строка для замены символов.
     * @return  новую строку, содержащую символы "о" вместо символов "а".
     */
    public String replaceAWithO (String string) {
        return string.replace("а", "о");
    }

    /**
     * Возвращает подстроку с 44 символа по 90 символ.
     * 
     * @param   string  строка для поиска подстроки с 44 по 90 символ.
     * @return  подстроку с 44 по 90 символ, или выводит сообщение о том, что строка слишком короткая,
     *          если ее длина меньше 90 символов.
     */
    public String substringFrom44to90 (String string) {
        if (string.length() < 90) {
            return "Слишком короткая строка! Нужно мимимум 90 символов";
        }
        return string.substring(43, 89);
    }

    /**
     * Разбивает строку по символу пробел (т.е. чтобы каждое слово было отдельным элементом массива).
     * 
     * @param   string  строка для разбития на массив.
     * @return  массив строк типа {@code String}.
     */
     public String[] splitOnSpace(String string) {
         return string.split(" ");
     }

    /**
     * Меняет последовательность символов в строке на обратную.
     * 
     * @param   string  строка для изменени последовательности символов на обратную.
     * @return  новую строку, содержащую те же символы, что и исходная, но в обратном порядке.
     */
    public String reverse (String string) {
        StringBuilder builder = new StringBuilder(string);
        return builder.reverse().toString();
    }
}

