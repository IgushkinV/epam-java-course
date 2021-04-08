package Igushkin.Homeworks;

import Igushkin.Homeworks.Lesson5.Filter;
import lombok.extern.slf4j.Slf4j;
import java.util.Scanner;

@Slf4j
public class Main {
    public static void main(String[] args) {
        log.info("Добро пожаловать! Вы в программе работы с файлами");
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
