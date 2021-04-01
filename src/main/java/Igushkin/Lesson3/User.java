package Igushkin.Lesson3;

import java.util.HashMap;

public class User {

    /**  Поле, enum.*/
    private Role role;
    private String name;
    private HashMap<Role, String> greetingsMap;

    public User(String name, Role role) {
        this.name = name;
        this.role = role;
        this.greetingsMap = new HashMap<>();
        this.greetingsMap.put(Role.ADMIN, "Администратор");
        this.greetingsMap.put(Role.USER, "Пользователь");
        this.greetingsMap.put(Role.MODERATOR, "Модератор");
    }

    public String getName() {
        return name;
    }

    public Role getRole() {
        return role;
    }

    public String getGreeting (Role role) {
        return greetingsMap.get(role);
    }
}
