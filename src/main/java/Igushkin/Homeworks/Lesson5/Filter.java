package Igushkin.Homeworks.Lesson5;

import lombok.extern.slf4j.Slf4j;

/**
 * Класс, содержащий в себе обработчики команд. Определяет, в какой обработчик должна быть передана команда.
 */
@Slf4j
public class Filter {
    /**
     * Поле. Строка, введенная пользователем.
     */
    private String line;
    /**
     * Поле. Содержит массив параметров, полученных из переданной строки.
     */
    private String[] params;
    /**
     * Поле. Содержит первый параметр из массива параметоров params.
     */
    private String command;
    /**
     * Поле. Инициализируется соответствующим обработчиком коимнад.
     */
    private CommandHandler commandHandler;

    /**
     * Проверяет строку на соответствие шаблону команд. Если неверные параметры, отклоняет запрос.
     * Распознает три команды: add, print, delete.
     * "add" - добавить текст в указанный файл;
     * "print" - распечатать текст из указанного файла;
     * "delete" - удалить строку из указанног файла.
     * После распознавания команды определяет сколько было передано параметров для этой комнды.
     * После определения команды и параметров вызывает соответствующий обработчик команд.
     * @param line
     */
    public void process(String line) {
        this.line = line;
        this.params = line.split(" ");
        this.command = params[0];
        switch (command) {
            case "add":
                String[] halfsOfInputLine = line.split("\"",2);
                if (halfsOfInputLine.length < 2 || !halfsOfInputLine[1].endsWith("\"")) {
                    log.warn("Неверный формат ввода. Текст для записи вводится в двойных кавычках.");
                    break;
                }
                this.params = halfsOfInputLine[0].split(" ");
                String stringToAdd = halfsOfInputLine[1].substring(0, halfsOfInputLine[1].length() - 1); //todo: Добавление текста без крайних кавычек
                if (params.length < 2) {
                    log.warn("Недостаточно параметров, повторите ввод.");
                    break;
                } else if (params.length > 3) {
                    log.warn("Неверный формат команды: слишком много аргументов.");
                    break;
                } else if (params.length == 2) {
                    commandHandler = new MyWriter(params[1], stringToAdd);
                } else if (params.length == 3) {
                    try {
                        int lineNumber = Integer.parseInt(params[1]);
                        if (lineNumber < 0) {
                            log.warn("Введен отрицательный номер строки, используйте неотрицательный");
                            break;
                        }
                        commandHandler = new MyWriter(lineNumber, params[2], stringToAdd);
                    } catch (NumberFormatException e) {
                        log.warn("Неверный формат номера строки. Введите номер цифрами 0-9.", e);
                    }
                }
                break;
            case "print":
                params = line.split(" ");
                if (params.length < 2 || params.length > 3) {
                    log.warn("Неверный набор параметров команды print");
                    break;
                } else if (params.length == 2) {
                    commandHandler = new MyPrinter(params[1]);
                } else {
                    try {
                        int lineNumber = Integer.parseInt(params[1]);
                        if (lineNumber < 0) {
                            log.warn("Введен отрицательный номер строки!");
                            return;
                        }
                        commandHandler = new MyPrinter(lineNumber, params[2]);
                    } catch (NumberFormatException e) {
                        log.warn("Неправильный формат номера строки! Введите цифрами 0-9", e);
                    }
                }
                break;
            case "delete":
                params = line.split(" ");
                if (params.length < 2 || params.length > 3) {
                    log.warn("Неверный набор параметров команды delete");
                    break;
                } else if (params.length == 2) {
                    commandHandler = new MyDeleter(params[1]);
                } else {
                    try {
                        int lineNumber = Integer.parseInt(params[1]);
                        if (lineNumber < 0) {
                            log.warn("Введен отрицательный номер строки!");
                            return;
                        }
                        commandHandler = new MyDeleter(lineNumber, params[2]);
                    } catch (NumberFormatException e) {
                        log.warn("Неправильный формат номера строки! Введите цифрами 0-9", e);
                    }
                }
                break;
            default:
                log.info("Команда не распознана, повторите ввод.");
                return;
        }
        if (commandHandler != null) {
            commandHandler.handle();
        }
    }
}
