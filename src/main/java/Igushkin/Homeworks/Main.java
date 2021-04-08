package Igushkin.Homeworks;

import Igushkin.Homeworks.Lesson5.Filter;
import lombok.extern.slf4j.Slf4j;
import java.util.Scanner;

/**
 * Класс для работы с файлами через консоль.
 */
@Slf4j
public class Main {
    public static void main(String[] args) {
        log.info("Добро пожаловать! Вы в программе работы с файлами. \n" +
                        "Поддерживаются команды: \n" +
                        "add [номер_строки] имя_файла \"текст для добавления в файл\" - вставляет текст указанной строкой либо в конец файла \n" +
                        "print [номер строки] имя_файла - печатает указанную строку из файла либо весь файл\n" +
                        "delete [номер строки] имя_файла - удаляет указанную строку либо последнюю в файле \n" +
                        "exit - завершение работы");
        Filter filter;
        Scanner scanner = new Scanner(System.in);
        filter = new Filter();
        while (true) {
            String line = scanner.nextLine();
            if (line.equals("exit")) {
                return;
            }
            filter.process(line);
        }
    }
}
