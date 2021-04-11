package Igushkin.Homeworks.Lesson5;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * Класс, реализующий обработчик команды print
 */
@Slf4j
public class MyPrinter implements CommandHandler {

    /**
     * Печатает конкретную строку либо весь файл в соответствии с параметрами, переданными в строке запроса пользователя.
     * @param line строка запроса пользователя
     */
    @Override
    public void handle(String line) {
        String[] params = line.split(" ");
        if (params.length < 2 || params.length > 3) {
            log.warn("Неверный набор параметров команды print");
            return;
        } else if (params.length == 2) { //Если запрос на печать всего файла, номер строки не передан.
            String fileName = params[1];
            if (!FileMethods.isFileExist(fileName)) {
                log.warn("Файл не найден.");
                return;
            }
            try {
                FileMethods.printFile(fileName);
            } catch (IOException e) {
                log.warn("Ошибка при печати файла {}", fileName);
            }
        } else { // Если запрос на печать конкретной строки, номер строки передан.
            String fileName = params[2];
            if (!FileMethods.isFileExist(fileName)) {
                log.warn("Файл не найден.");
                return;
            }
            try {
                int lineNumber = Integer.parseInt(params[1]);
                if (lineNumber < 0) {
                    log.warn("Введен отрицательный номер строки!");
                    return;
                }
                FileMethods.printLineFormFile(lineNumber, fileName);
            } catch (NumberFormatException e) {
                log.warn("Неправильный формат номера строки! Введите цифрами 0-9", e);
            } catch (WrongLineNumberException e) {
                log.warn("Номер строки больше, чем количество строк в файле", e);
            } catch (IOException e) {
                log.warn("Ошибка при чтении файла", e);
            }
        }
    }
}
