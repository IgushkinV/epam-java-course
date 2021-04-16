package Igushkin.Homeworks;

import Igushkin.Homeworks.lesson7.CustomClassLoader;
import java.lang.reflect.InvocationTargetException;

public class Main {
    private static final String PATH = "C:/myClasses/";
    private static final String FILE_EXTENSION = ".class";
    private static final String CLASS_NAME = "TestClass";

    public static void main(String[] args) {
        CustomClassLoader loader = new CustomClassLoader(PATH);
        try {
            Class clazz = loader.loadClass(CLASS_NAME);
            Object testClass = clazz.getDeclaredConstructor().newInstance();
            testClass.toString();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}
