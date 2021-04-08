package Igushkin.Homeworks.Lesson5;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.ArrayList;

/**
 * Класс, реализующий обработчик команды print
 */
@Slf4j
public class MyPrinter implements CommandHandler {

    private int lineNumber;
    private String fileName;

    public MyPrinter (String fileName) {
        this.lineNumber = -1;
        this.fileName = fileName;
    }
    public MyPrinter (int lineNumber, String fileName)  {
        this.lineNumber = lineNumber;
        this.fileName = fileName;
    }

    /**
     * Печатает строку либо весь файл, соответственно переданным в конструктор параметрам.
     */
    @Override
    public void handle() {
        if (!FileMethods.isFileExist(fileName)) {
            log.warn("Файл не найден.");
            return;
        }
        try {
            ArrayList<String> linesFromFile = FileMethods.readFileToList(fileName);
            if (this.lineNumber == -1) {
                for (String line : linesFromFile) {
                    log.info(line);
                }
            } else if (lineNumber > linesFromFile.size()){
                log.warn("Номер строки превышает количество строк в файле! Номер последней строки {}", linesFromFile.size() - 1);
                return;
            } else {
                log.info(linesFromFile.get(lineNumber));
            }
        } catch (FileNotFoundException e) {
            log.warn("Не найден файл с именем {} ", fileName, e);
        } catch (IOException e) {
            log.warn("Ошибка при чтении файла {}", fileName, e);
        }
    }
}
