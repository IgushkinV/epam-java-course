package Igushkin.Homeworks.Lesson5;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WrongCommandHandler implements CommandHandler{

    @Override
    public void handle(String command) {
        log.warn("Команда не распознана. Повторите ввод.");
    }
}
