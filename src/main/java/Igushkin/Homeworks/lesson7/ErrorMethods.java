package Igushkin.Homeworks.lesson7;

import java.util.ArrayList;

/**
 * Contains methods that cause OOM StackOverFlow errors.
 */
public class ErrorMethods {

    public static void methodWithStackOverflowError() {
        new ArrayList();
        methodWithStackOverflowError();
    }

    public static void methodWithOOMError() {
        int i = 0;
        ArrayList list = new ArrayList();

        while(true) {
            String str = i + "";
            list.add(str);
            ++i;
        }
    }
}
