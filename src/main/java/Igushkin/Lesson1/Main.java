package Igushkin.Lesson1;

public class Main {
    public static void main(String[] args) {
        MyStringMethods methods = new MyStringMethods();
        String helloWorld = methods.getString("Hello World!"); //3. Создать новую строку с помощью конструктора и занести ее в пул литералов
        String helloWorldLowerCase = methods.getString("hellowworld!");
        String longString = "public String intern()\n" +
                "Returns:\n" +
                "a string that has the same contents as this string, but is guaranteed to be from a pool of unique strings.";
        System.out.println(helloWorld); //Вывод "Hello World!"
        int lengthOfString = methods.getLength(helloWorld); //1.Получить длину строки.
        boolean EqualsWOCase = methods.equalsWOutCase(helloWorld, helloWorldLowerCase); //2. Сравнить 2 строки без учета регистра
        System.out.println(helloWorld.intern()); //3.1. Вывести содержимое переменной из пула литералов
        char[] charArr = methods.getCharArray(helloWorld); //4. Получить из строки массив символов.
        byte[] byteArr = methods.getByteArray(helloWorld); //5. Получить из строки массив байтов
        String helloUpper = methods.toUpperCase(helloWorld); //6. Привести строку к верхнему регистру
        int numberFirstA = methods.FirstAChar("Булочка с изюмами."); //7. Найти первую позицию символа "а" в строке
        int numberLastA = methods.LastAChar("Булочка с изюмами."); //8. Найти последнюю позицию символа "а" в строке
        boolean containsSun = methods.containsSun("The Sun is shining"); //Проверить содержит-ли строка слово "Sun"
        boolean endsWithOracle = methods.isEndsWithOracle("Java was created by Oracle"); //10. Проверить оканчивается-ли строка на "Oracle"
        boolean startsWithJava = methods.isStarsWithJava("Java was created by Oracle"); //11. Проверить начинается-ли строка на "Java"
        String replaceAO = methods.replaceAWithO("Здесь нет символа 'а'."); //12.Заменить все символы "а" в строке на символ "о"
        String subStr = methods.substringFrom44to90(longString); //13. Получить подстроку с 44 символа по 90 символ
        //System.out.println(subStr);
        String[] stringsArray = methods.splitOnSpace(helloWorld); //14. Разбить строку по символу пробел
                                                                // (т.е. чтобы каждое слово было отдельным элементом массива)
        String reverseStr = methods.reverse("123456789"); //15. Поменять последовательность символов в строке на обратную.






    }


}
