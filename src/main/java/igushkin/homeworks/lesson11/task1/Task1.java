package igushkin.homeworks.lesson11.task1;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Task1 {

    static class A {

        private String stringA = "A";

        synchronized public void setStringA(String s) {
            this.stringA = s;
        }

        synchronized String getString() {
            return stringA;
        }

        synchronized void setterB(B classB) {
            for (var i = 0; i < 10000; i++) {
            }
            classB.setStringB("New Value B");
        }
    }

    static class B {
        private String stringB = "B";

        synchronized public void setStringB(String s) {
            this.stringB = s;
        }

        synchronized String getString() {
            return stringB;
        }

        synchronized void setterA(A classA) {
            for (var i = 0; i < 10000; i++) {
            }
            classA.setStringA("New Value A");
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
        }, "DeadLockThread-1");

        Thread thread2 = new Thread(() -> {
            log.info("makeDeadLock() - Starting thread2.");
            log.info("makeDeadLock() - Получаем поле класса B. {}", classB.getString());
            log.info("makeDeadLock() - Устанавливаем поле B, вызывая метод класса A.");
            classA.setterB(classB);
            log.info("makeDeadLock() - Успешно установили значение в поле класса B: {}", classB.getString());
        }, "DeadLockThread-2");
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
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    log.error("demonstrateRaceCondition() - error", e);
                }
            }
        }, "RaceConditionThread-1");

        Thread printEvenNumThread = new Thread(() -> {
            while (num < 20) {
                log.info("Проверка числа на четность, num = {}", num);
                if (num % 2 == 0) {
                    log.info("Проверка четности прошла успешно.");
                    log.info("Четное число num: {}", num);
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    log.error("demonstrateRaceCondition() - error: ", e);
                }
            }
        }, "RaceConditionThread-2");

        incrementThread.start();
        printEvenNumThread.start();
    }
}
