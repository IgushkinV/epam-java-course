package Igushkin.Homeworks.Lesson5;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.ArrayList;

/**
 * Класс для обработки переданной команды и записи переданного текста в файл.
 */
@Slf4j
public class MyWriter implements CommandHandler {

    private int lineNumber;
    private String fileName;
    private String textToWrite;

    public MyWriter(String fileName, String textToWrite) {
        this.fileName = fileName;
        this.textToWrite = textToWrite;
        this.lineNumber = -1;
        log.debug("Создан Writer для записи \"{}\" в конец файла {}", textToWrite, fileName);
    }
    public MyWriter (int lineNumber, String fileName, String textToWrite) {
        this.lineNumber = lineNumber;
        this.fileName = fileName;
        this.textToWrite = textToWrite;
        log.debug("Создан Writer для записи \"{}\" в файл {} начиная с {} строки", textToWrite, fileName, lineNumber);
    }

    /**
     * Добавляет строку либо в конец файла, либо на указанную позицию в соответствии с переданными в конструктор параметрами.
     */
    @Override
    public void handle() {
        if (this.lineNumber == -1) {
            //Если добавляем запись в конец файла, номер строки не передан.
            try {
                FileMethods.writeStringToEnd(textToWrite, fileName);
            } catch (IOException e) {
                log.warn("Ошибка при попытке записи в файл", e);
            }
        } else {
            //Если номер строки передан
            try {
                FileMethods.stringWriter(lineNumber, textToWrite, fileName);
            } catch (IOException e) {
                log.warn("Ошибка при записи в файл.", e);
            }
        }
    }
}
