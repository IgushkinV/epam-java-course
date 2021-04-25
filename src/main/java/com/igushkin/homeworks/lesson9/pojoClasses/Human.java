package com.igushkin.homeworks.lesson9.pojoClasses;

import com.igushkin.homeworks.lesson9.AnnotationProcessor;
import com.igushkin.homeworks.lesson9.annotations.Entity;
import com.igushkin.homeworks.lesson9.annotations.Value;

/**
 * POJO class.
 */
@Entity
public class Human {
    @Value("10")
    private int age;
    @Value("Strange")
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

    @Value("Not So Strange")
    public void setName(String name) {
        this.name = name;
    }
}
