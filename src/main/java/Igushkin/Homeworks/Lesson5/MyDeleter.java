package Igushkin.Homeworks.Lesson5;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.ArrayList;

/**
 * Класс для реализации обработки команды delete.
 */
@Slf4j
public class MyDeleter implements CommandHandler {

    int lineNumber;
    String fileName;

    public MyDeleter(String fileName) {
        this.lineNumber = -1;
        this.fileName = fileName;
    }
    public MyDeleter(int lineNumber, String fileName) {
        this.lineNumber = lineNumber;
        this.fileName = fileName;
    }

    /**
     * Удалаяет конкретную строку либо последнюю строку файла в соответствии с переданными в конструктор параметрами.
     */
    @Override
    public void handle() {
        if (!FileMethods.isFileExist(fileName)) {
            log.warn("Файл не найден.");
            return;
        }
        try {
            ArrayList<String> linesFromFile = FileMethods.readFileToList(fileName);
            //Удалить только последний элемент - только он не перезаписывается.
            if (this.lineNumber == -1) {
                linesFromFile.remove(linesFromFile.size() - 1);
                FileMethods.writeArrayListToFile(linesFromFile, fileName);
                //Если передан номер, превышающий количество строк в файле.
            } else if (lineNumber > linesFromFile.size()){
                log.warn("Номер строки превышает количество строк в файле! Номер последней строки {}", linesFromFile.size() - 1);
                return;
            //Удалить только один элемент
            } else {
                linesFromFile.remove(lineNumber);
                FileMethods.writeArrayListToFile(linesFromFile, fileName);
            }
            } catch (FileNotFoundException e) {
                log.warn("Не найден файл с именем {} ", fileName, e);
            } catch (IOException e) {
                log.warn("Ошибка при чтении файла {}", fileName, e);
        }
    }
}
