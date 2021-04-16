package Igushkin.Homeworks;

import Igushkin.Homeworks.lesson7.CustomClassLoader;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

/**
 * Demonstration of a custom class loader. Loads the specified .class file from the specified directory.
 */
@Slf4j
public class Main {
    private static final String PATH = "C:/myClasses/";
    private static final String CLASS_NAME = "TestClass";

    public static void main(String[] args) {
        CustomClassLoader loader = new CustomClassLoader(PATH);
        try {
            Class clazz = loader.loadClass(CLASS_NAME);
            Object testClass = clazz.getDeclaredConstructor().newInstance();
            testClass.toString();
        } catch (ClassNotFoundException e) {
            log.warn("Error while loading Class from file.", e);
        } catch (NoSuchMethodException e) {
            log.warn("Constructor of the class not found.", e);
        } catch (IllegalAccessException e) {
            log.warn("Error while instantiating class.",e);
        } catch (InstantiationException e) {
            log.warn("Error while instantiating class.", e);
        } catch (InvocationTargetException e) {
            log.warn("Error while instantiating class.", e);
        }

    }
}
