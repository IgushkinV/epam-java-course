package Igushkin.Homeworks.Lesson5;

import lombok.extern.slf4j.Slf4j;
import java.util.HashMap;

/**
 * Класс, содержащий в себе обработчики команд. Определяет, в какой обработчик должна быть передана команда.
 */
@Slf4j
public class Filter {

    /**
     * Поле. Содержит список команд и соответствующих им обработчиков.
     */
    private HashMap<Command, CommandHandler> handlersMap = new HashMap<>();

    public Filter() {
        this.handlersMap.put(Command.ADD, new MyWriter());
        this.handlersMap.put(Command.PRINT, new MyPrinter());
        this.handlersMap.put(Command.DELETE, new MyDeleter());
        this.handlersMap.put(Command.WRONG, new WrongCommandHandler());
    }

    /**
     * Проверяет, какая команда передана первой в строке запроса пользователя. Если несуществующая команда, отклоняет запрос.
     * Распознает три команды: add, print, delete.
     * "add" - добавить текст в указанный файл;
     * "print" - распечатать текст из указанного файла;
     * "delete" - удалить строку из указанног файла.
     * После распознавания команды передает строку запроса пользвотеля в соответствующий обработчик.
     * @param line строка запроса пользователя
     */
    public void process(String line) {
        String[] params = line.split(" ");
        if (params == null || params.length == 0) {
            handlersMap.get(Command.WRONG).handle(line);
            return;
        }
        String command = params[0];
        handlersMap.getOrDefault(Command.getCommandByString(command), handlersMap.get(Command.WRONG)).handle(line);
    }
}
