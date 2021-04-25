package com.igushkin.homeworks.lesson9.pojoClasses;

import com.igushkin.homeworks.lesson9.annotations.Value;

/**
 * POJO class.
 */
public class Cat {
    private int age;
    @Value("Rex")
    private String name;

    public int getAge() {
        return age;
    }

    @Value
    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Value
    public void doNothing() {

    }
}

