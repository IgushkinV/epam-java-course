package Igushkin.Homeworks.Lesson5;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.ArrayList;

@Slf4j
public class MyDeleter implements CommandHandler {

    int lineNumber;
    String fileName;

    public MyDeleter(String fileName) {
        this.lineNumber = -1;
        this.fileName = fileName;
        log.debug("Создан Deleter для файла: {}", fileName);

    }
    public MyDeleter(int lineNumber, String fileName) {
        this.lineNumber = lineNumber;
        this.fileName = fileName;
    }
    @Override
    public void handle() {
        ArrayList<String> linesFromFile = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                linesFromFile.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            //Удалить только последний элемент - только его не перезапишем.
            if (this.lineNumber == -1) {
                linesFromFile.remove(linesFromFile.size() - 1);
                for (String line : linesFromFile) {
                    writer.write(line + "\n");
                }
                writer.flush();
                //Если передан номер, превышающий количество строк в файле.
            } else if (lineNumber > linesFromFile.size()){
                log.warn("Номер строки превышает количество строк в файле! Номер последней строки {}", linesFromFile.size() - 1);
                return;
            //Удалить только один элемент
            } else {
                linesFromFile.remove(lineNumber);
                for (String line : linesFromFile) {
                    writer.write(line + "\n");
                }
                writer.flush();
            }
            } catch (FileNotFoundException e) {
                log.warn("Не найден файл с именем {} ", fileName, e);
            } catch (IOException e) {
                log.warn("Ошибка при чтении файла {}", fileName, e);
        }
    }

}
