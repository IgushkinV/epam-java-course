package com.igushkin.homeworks;


import com.igushkin.homeworks.lesson9.pojoClasses.Cat;
import com.igushkin.homeworks.lesson9.pojoClasses.Human;
import com.igushkin.homeworks.lesson9.annotations.Entity;
import com.igushkin.homeworks.lesson9.annotations.Value;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;

import com.igushkin.homeworks.lesson9.exceptions.NoValueAnnotationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    final static Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            handlePojo(new Human());
            handlePojo(new Cat());
        } catch (IllegalAccessException e) {
            log.error("main() - Error in class's annotations, check them!", e);
        } catch (NoSuchFieldException e) {
            log.error("main() - Error in class's annotations, check them!", e);
        }
    }

    public static boolean hasEntityAnnotation (Object pojoObject) {
        log.debug("hasEntityAnnotation() - starting to check if the class {} has annotation @Entity",
                pojoObject.getClass().getSimpleName());
        boolean result = false;
        Class<?> aClass = pojoObject.getClass();
        Entity entityAnnotation = aClass.getAnnotation(Entity.class);
        if (Objects.nonNull(entityAnnotation)) {
            result = true;
        }
        log.debug("hasEntityAnnotation() - result of this check - {}", result);
        return result;
    }

    public static boolean hasAnyFieldValueAnnotated(Object pojoObject) {
        log.debug("hasFieldsValueAnnotation() - starting to check if fields of the class {} has annotation @Value",
                pojoObject.getClass().getSimpleName());
        boolean result = false;
        Field[] declaredFields = pojoObject.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            Value annotation = field.getAnnotation(Value.class);
            if (Objects.nonNull(annotation)) {
                result = true;
            }
        }
        log.debug("hasFieldsValueAnnotation() - result of this check - {}", result);
        return result;
    }
    public static boolean hasAnyMethodValueAnnotated(Object pojoObject) {
        log.debug("hasMethodsValueAnnotation() - starting to check if methods of the class {} has annotation @Value",
                pojoObject.getClass().getSimpleName());
        boolean result = false;
        Method[] declaredMethods = pojoObject.getClass().getDeclaredMethods();
        for (Method method : declaredMethods) {
            Value annotation = method.getAnnotation(Value.class);
            if (Objects.nonNull(annotation)) {
                result = true;
            }
        }
        log.debug("hasMethodsValueAnnotation() - result of this check - {}", result);
        return result;
    }

    public static String makeSetterName(Field field) {
        String fieldName = field.getName();
        StringBuilder builder = new StringBuilder(fieldName);
        builder.setCharAt(0, Character.toUpperCase(fieldName.charAt(0)));
        return "set" + builder.toString();
    }

    public static void setInt(Field field, Object pojoObject, Value annotation) throws IllegalAccessException {
        field.setAccessible(true);
        log.trace("setInt() - annotation.value() = {}", annotation.value());
        int value = Integer.parseInt(annotation.value());
        field.set(pojoObject, value);
    }

    public static void setString(Field field, Object pojoObject, Value annotation) throws IllegalAccessException {
        field.setAccessible((true));
        log.trace("setString() - annotation.value() = {}", annotation.value());
        String value = annotation.value();
        field.set(pojoObject, value);
    }
    public static boolean setFieldFromSetter(Field field, Object pojoObject) {
        log.debug("setFieldFromSetter() - start to search setter to field {}", field.getName());
        log.trace("setFieldFromSetter() - field type is {}", field.getType());
        log.trace("field type .equals(int) {}", field.getType().equals(int.class));
        log.trace("field type .equals(String) {}", field.getType().equals(String.class));
        boolean result = false;
        try {
            String setterName = makeSetterName(field);
            log.debug("setFieldFromSetter() - searching for annotated @Value method {}", setterName);
            Method setterMethod = pojoObject.getClass().getDeclaredMethod(setterName, field.getType());
            Value setterAnnotation = setterMethod.getAnnotation(Value.class);
            if (Objects.nonNull(setterAnnotation)) {
                if (field.getType().equals(int.class)) {
                    setInt(field, pojoObject, setterAnnotation);
                } else if (field.getType().equals(String.class)) {
                    setString(field, pojoObject, setterAnnotation);
                }
                result = true;
                log.debug("setFieldFromSetter() - successful set value {} to field {}",
                        setterAnnotation.value(), field.getName());
            }
        } catch (NoSuchMethodException e) {
            result = false;
            log.warn("setFieldFromSetter() - no annotated @Value setter found!", e);
        } catch (IllegalAccessException e) {
            result = false;
            log.warn("setFieldFromSetter() - fail to set field from setter", e);
        } catch (NumberFormatException e) {
            result = false;
            log.warn("setFieldFromSetter() - wrong value in @Value of setter", e);
        }
        log.trace("setFieldFromSetter() - success: {}", result);
        return result;
    }

    public static boolean setFromFieldAnnotation (Field field, Object pojoObject) throws IllegalAccessException {
        log.debug("setFromFieldAnnotation() - start to search @Value on field {}", field.getName());
        log.trace("setFromFieldAnnotation() - field type is {}", field.getType());
        boolean result = false;
        Value value = field.getAnnotation(Value.class);
        if (Objects.nonNull(value)) {
            try {
                if (field.getType().equals(int.class)) {
                    setInt(field, pojoObject, value);
                    result = true;
                } else if (field.getType().equals(String.class)) {
                    setString(field, pojoObject, value);
                    result = true;
                }
            } catch (IllegalAccessException e) {
                log.warn("setFromFieldAnnotation() - fail to set field from field's @Value");
            }
        }
        log.debug("setFromFieldAnnotation() - setting success: {}, field's value: {}", result, field.get(pojoObject));
        return result;
    }
    //todo: завершить логику с возвратом типа boolean
    public static void handlePojo(Object pojoObject) throws IllegalAccessException, NoSuchFieldException {
        if (hasEntityAnnotation(pojoObject)) {
            if (!hasAnyFieldValueAnnotated(pojoObject) || !hasAnyMethodValueAnnotated(pojoObject)) {
                log.debug("handleHuman() - no @Value annotation on fields or methods! " +
                        "Throwing NoValueAnnotationException");
                throw new NoValueAnnotationException();
            } else {
                Field[] declaredFields = pojoObject.getClass().getDeclaredFields();
                for (Field field : declaredFields) {
                    if (setFieldFromSetter(field, pojoObject)) {
                        log.trace("handleHuman() - setFieldFromSetter() = true, field: {}", field.getName());
                    } else if (setFromFieldAnnotation(field, pojoObject)) {
                        log.trace("handleHuman() - setFromFieldAnnotation() = true, field: {}", field.getName());

                    } else { //set field by default

                    }
                }
            }
        } else {
            if (hasAnyFieldValueAnnotated(pojoObject) || hasAnyMethodValueAnnotated(pojoObject)) {
                log.debug("handleHuman() - The class {} has no @Entity annotation, \n" +
                        "but some field or method has @Value annotation, throwing IllegalAccessException.",
                        pojoObject.getClass().getSimpleName());
                throw new IllegalAccessException();
            }
        }
    }
}
