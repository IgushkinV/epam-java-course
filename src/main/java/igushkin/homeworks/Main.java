package igushkin.homeworks;

import igushkin.homeworks.lesson11.task1.Task1;
import igushkin.homeworks.lesson11.task2.Task2;
import igushkin.homeworks.lesson11.task3.Chat;

public class Main {
    public static void main(String[] args) {
        Task1 task1 = new Task1();
        task1.demonstrateDeadLock();
        task1.demonstrateRaceCondition();

        Task2 task2 = new Task2();
        task2.demonstrateDeadLock();
        task2.demonstrateRaceCondition();

        Chat chat = new Chat();

        chat.startWriters();
        chat.startReaders();
        chat.startUpdaters();

    }
}
