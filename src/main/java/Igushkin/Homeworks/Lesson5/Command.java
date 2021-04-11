package Igushkin.Homeworks.Lesson5;

import java.util.HashMap;
import java.util.Map;

public enum Command {
    ADD ("add"),
    PRINT ("print"),
    DELETE ("delete");

    private final String command;

    Command (String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    private static final Map<String, Command> COMMAND_MAP = new HashMap<>();

    static {
        for (Command com : values()) {
            COMMAND_MAP.put(com.getCommand(), com);
        }
    }
    public static Command getCommandByString (String str) {
        return COMMAND_MAP.get(str);
    }
}
