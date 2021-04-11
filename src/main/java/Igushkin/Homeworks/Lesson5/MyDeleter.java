package Igushkin.Homeworks.Lesson5;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.ArrayList;

/**
 * Класс для реализации обработки команды delete.
 */
@Slf4j
public class MyDeleter implements CommandHandler {

    /**
     * Удалаяет конкретную строку либо последнюю строку файла. Для этого обрабатывает переданную команду.
     * В зависимости от количества переданных аргументов выбирает способ удаления.
     * Перед выполнением удаления проверят аргументы на корректность.
     *
     */
    @Override
    public void handle(String line) {
        String[] params = line.split(" ");
        if (params.length < 2 || params.length > 3) {
            log.warn("Неверный набор параметров команды delete");
            return;
        } else if (params.length == 2) {
            String fileName = params[1];
            if (!FileMethods.isFileExist(fileName)) {
                log.warn("Файл не найден.");
                return;
            }
            ArrayList<String> linesFromFile = null;
            try {
                linesFromFile = FileMethods.readFileToList(fileName);
            } catch (IOException ioException) {
                log.warn("Ошибка при удалении последней строки файла. Не получилось прочитать файл.", ioException);
            }
            //Удалить только последний элемент - только он не перезаписывается.
            if (linesFromFile.size() == 0) {
                log.warn("Нечего удалить из пустого файла!");
                return;
            }
            linesFromFile.remove(linesFromFile.size() - 1);
            try {
                FileMethods.writeArrayListToFile(linesFromFile, fileName);
            } catch (IOException ioException) {
                log.warn("Ошибка при удалении последней строки файла. Не получилось записать в файл.", ioException);
            }
        } else { //Передано 3 параметра в строке line, удаляем строку с переданным номером
            try {
                int lineNumber = Integer.parseInt(params[1]);
                String fileName = params[2];
                if (lineNumber < 0) {
                    log.warn("Введен отрицательный номер строки!");
                    return;
                }
                if (!FileMethods.isFileExist(fileName)) {
                    log.warn("Файл не найден.");
                    return;
                }
                ArrayList<String> linesFromFile = FileMethods.readFileToList(fileName);
                if (lineNumber > linesFromFile.size()) {
                    log.warn("Номер строки превышает количество строк в файле! Номер последней строки {}", linesFromFile.size() - 1);
                    return;
                }
                linesFromFile.remove(lineNumber);
                FileMethods.writeArrayListToFile(linesFromFile, fileName);
            } catch (NumberFormatException e) {
                log.warn("Неправильный формат номера строки! Введите цифрами 0-9", e);
            } catch (IOException e) {
                log.warn("Ошибка во время удаления строки из файла.", e);
            }
        }
    }
}