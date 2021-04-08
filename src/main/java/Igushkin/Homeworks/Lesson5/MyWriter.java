package Igushkin.Homeworks.Lesson5;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.ArrayList;

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
    public MyWriter (int lineNumber, String fileName, String textToWrite) throws IOException {
        if (lineNumber < 0) {
            throw new IOException("Введен отрицательный номер строки");
        }
        this.lineNumber = lineNumber;
        this.fileName = fileName;
        this.textToWrite = textToWrite;
        log.debug("Создан Writer для записи \"{}\" в файл {} начиная с {} строки", textToWrite, fileName, lineNumber);
    }

    @Override
    public void handle() {
        if (this.lineNumber == -1) {
            //Если добавляем записть в конец файла, номер строки не передан.
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName,true))) {
                writer.write(this.textToWrite + "\n");
            } catch (IOException e) {
                log.warn("Ошибка при попытке записи в файл", e);
            }
        } else {
            //Если номер строки передан
            ArrayList<String> linesFromFile = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                while (reader.ready()) {
                    linesFromFile.add(reader.readLine());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            //2 варианта записи
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                //1 вариант - запись между существующих строк. Отсчет строк с 0.
                if (lineNumber < linesFromFile.size()) {
                    for (int i = 0; i < linesFromFile.size(); i++) {
                        if (lineNumber == i) {
                            writer.write(textToWrite + "\n");
                        }
                        writer.write(linesFromFile.get(i) + "\n");
                    }
                    //2 вариант - запись после существующих строк. Добавляет пустые при необходимости. Отсчет строк с 0.
                } else {
                    for (String line : linesFromFile) {
                        writer.write(line + "\n");
                    }
                    for (int i = linesFromFile.size() + 1; i <= lineNumber; i++) {
                        writer.newLine();
                    }
                    writer.write(textToWrite + "\n");
                }
                writer.flush();
            } catch (IOException e) {
                log.warn("Ошибка при записи в файл.", e);
            }
        }
    }
}
