package igushkin.homeworks.lesson11.task2;

import lombok.extern.slf4j.Slf4j;

/**
 * Demonstrates resolving deadlock and race condition problems.
 * Deadlock is solved by adding an object that serves to synchronize.
 * Race condition is solved by using a local copy of the variable.
 */
@Slf4j
public class Task2 {

    public static volatile Object synchroObject = new Object();

    static class A {

        private String stringA = "A";

        public void setStringA(String s) {
            synchronized (synchroObject) {
                this.stringA = s;
            }
        }

        public String getString() {
            synchronized (synchroObject) {
                return stringA;
            }
        }

        public void setterB(B classB) {
            synchronized (synchroObject) {
                for (var i = 0; i < 10000; i++) {
                }
                classB.setStringB("New Value B");
            }
        }
    }

    static class B {
        private String stringB = "B";

        public void setStringB(String s) {

            synchronized (synchroObject) {
                this.stringB = s;
            }
        }

        public String getString() {
            synchronized (synchroObject) {
                return stringB;
            }
        }

        public void setterA(A classA) {
            synchronized (synchroObject) {
                for (var i = 0; i < 10000; i++) {
                }
                classA.setStringA("New Value A");
            }
        }
    }

    public void demonstrateDeadLock() {
        A classA = new A();
        B classB = new B();

        Thread thread1 = new Thread(() -> {
            log.info("makeDeadLock() - Starting thread1.");
            log.info("makeDeadLock() - Получаем поле класса A. {}", classA.getString());
            log.info("makeDeadLock() - Устанавливаем поле А, вызывая метод класса B.");
            classB.setterA(classA);
            log.info("makeDeadLock() - Успешно установили значение в поле класса A: {}", classA.getString());
        });

        Thread thread2 = new Thread(() -> {
            log.info("makeDeadLock() - Starting thread2.");
            log.info("makeDeadLock() - Получаем поле класса B. {}", classB.getString());
            log.info("makeDeadLock() - Устанавливаем поле B, вызывая метод класса A.");
            classA.setterB(classB);
            log.info("makeDeadLock() - Успешно установили значение в поле класса B: {}", classB.getString());
        });
        thread1.start();
        thread2.start();
    }

    static int num;

    public void demonstrateRaceCondition() {
        num = 0;
        Thread incrementThread = new Thread(() -> {
            while (num < 20) {
                log.info("До увеличения num = {}", num);
                num++;
                log.info("Произошло увеличение num на 1, новое значение: {}", num);
            }
        });

        Thread printEvenNumThread = new Thread(() -> {
            while (num < 20) {
                var localCopy = num;
                log.info("Проверка числа на четность, num = {}", localCopy);
                if (localCopy % 2 == 0) {
                    log.info("Проверка четности прошла успешно.");
                    log.info("Четное число num: {}", localCopy);
                }
            }
        });

        incrementThread.start();
        printEvenNumThread.start();
    }
}
