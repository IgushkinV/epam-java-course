package Igushkin.Homeworks.Lesson5;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.ArrayList;

@Slf4j
public class MyPrinter implements CommandHandler {

    private int lineNumber;
    private String fileName;

    public MyPrinter (String fileName) {
        this.lineNumber = -1;
        this.fileName = fileName;
        log.debug(fileName);
    }
    public MyPrinter (int lineNumber, String fileName)  {
        this.lineNumber = lineNumber;
        this.fileName = fileName;
    }
    @Override
    public void handle() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            ArrayList<String> linesFromFile = new ArrayList<>();
            while (reader.ready()) {
                linesFromFile.add(reader.readLine());
            }
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
