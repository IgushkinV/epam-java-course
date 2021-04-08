package Igushkin.Homeworks.Lesson5;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyDeleter implements CommandHandler {

    int number;
    String filename;

    public MyDeleter(String fileName) {
        this.number = -1;
        this.filename = fileName;
        log.info("Создан Deleter для файла: {}", fileName);

    }
    public MyDeleter(int number, String fileName) {
        this.number = number;
    }
    @Override
    public void handle() {

    }
}
