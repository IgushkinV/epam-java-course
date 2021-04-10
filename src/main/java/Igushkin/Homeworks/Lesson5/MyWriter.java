package Igushkin.Homeworks.Lesson5;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.ArrayList;

/**
 * Класс для обработки переданной команды и записи переданного текста в файл.
 */
@Slf4j
public class MyWriter implements CommandHandler {

    /**
     * Добавляет строку в файл в соответствии с параметрами, переданными в строке запроса пользователя.
     * Проверяет строку на правильность запроса.
     * Если номер строки не передан, то добавит строку в конец файла.
     * Если номер строки передан и он меньше количества строк в файле, то запишет строку со сдвигом существующих.
     * если номер строки передан и он больше количества строк в файле, то дополнит пустыми строками, а потом запишет переданную строку.
     * @param line строка запроса пользователя
     */
    @Override
    public void handle(String line) {
        int lineNumber = -1;
        String[] halfsOfLine = line.split("\"",2); //Разделение на параметры команды и текст после кавычки.
        if (halfsOfLine.length < 2 || !halfsOfLine[1].endsWith("\"")) {
            log.warn("Неверный формат ввода. Текст для записи вводится в двойных кавычках.");
            return;
        }
        String[] params = halfsOfLine[0].split(" "); //строка содержит команды только до первой кавычки
        String stringToAdd = halfsOfLine[1].substring(0, halfsOfLine[1].length() - 1);
        if (params.length < 2) {
            log.warn("Недостаточно параметров, повторите ввод.");
            return;
        } else if (params.length > 3) {
            log.warn("Неверный формат команды: слишком много аргументов.");
            return;
        } else if (params.length == 2) { //Если передан текст для записи без номера строки, на которую записать.
            String fileName = params[1];
            try {
                FileMethods.writeStringToEnd(fileName, stringToAdd);
            } catch (IOException e) {
                log.warn("Ошибка при записи текста {} в конец файла {}", stringToAdd, fileName);
            }
        } else { //Если передан номер строки, на которую записать, и текст для записи.
            try {
                lineNumber = Integer.parseInt(params[1]);
                String fileName = params[2];
                if (lineNumber < 0) {
                    log.warn("Введен отрицательный номер строки, используйте неотрицательный.");
                    return;
                }
                ArrayList<String> listFromFile = FileMethods.readFileToList(fileName);
                if (lineNumber < listFromFile.size()) {
                    FileMethods.writeInsideFile(lineNumber, fileName, stringToAdd);
                } else {
                    FileMethods.writeAfterFile(lineNumber, fileName, stringToAdd);
                }
            } catch (NumberFormatException e) {
                log.warn("Неверный формат номера строки. Введите номер цифрами 0-9.", e);
            } catch (IOException e) {
                log.warn("Ошибка при записи текста \"{}\" в файл {} на строку № {}",stringToAdd, params[2], lineNumber);
            }
        }
    }
}
