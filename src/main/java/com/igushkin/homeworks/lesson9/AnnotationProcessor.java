package com.igushkin.homeworks.lesson9;

import com.igushkin.homeworks.lesson9.annotations.Entity;
import com.igushkin.homeworks.lesson9.annotations.Value;
import com.igushkin.homeworks.lesson9.exceptions.NoValueAnnotationException;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

import com.igushkin.homeworks.lesson9.exceptions.TypeUnsupportedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Checks if the class has @Entity and @Value annotation, sets value of @Value annotation to fields.
 * Works only with String and Integer fields.
 */
public class AnnotationProcessor {
    final static Logger log = LoggerFactory.getLogger(AnnotationProcessor.class);

    private final static String STRING_DEFAULT = "default";
    private final static int INT_DEFAULT = -1;

    /**
     * Field. Contains path-value pairs, retrieved from the file by fullPath.
     */
    private Map<String, String> pathValueMap;

    public void setPathValueMap(Map<String, String> map) {

        this.pathValueMap = map;
    }

    /**
     * Checks if the pojoObject has both @Entity and @Value annotations.
     * If that's the case, sets field's annotated @Value to the annotation value.
     *
     * @param pojoObject POJO class instance.
     * @throws IllegalStateException      when the passed object has no @Entity annotation and has a @Value annotation.
     * @throws NoValueAnnotationException when the passed object has an @Entity annotation
     *                                    and none of its fields or setters not annotated by @Value annotation.
     */
    public void handlePojo(Object pojoObject) throws NoValueAnnotationException, IllegalStateException {
        if (hasEntityAnnotation(pojoObject)) {
            Method[] declaredMethods = pojoObject.getClass().getDeclaredMethods();
            Field[] declaredFields = pojoObject.getClass().getDeclaredFields();
            if (!hasValueAnnotation(declaredFields) && !hasValueAnnotation(declaredMethods)) {
                log.debug("handlePojo() - no @Value annotation on fields or methods! " +
                        "Throwing NoValueAnnotationException.");
                throw new NoValueAnnotationException();
            } else {
                for (Field field : declaredFields) {
                    try {
                        setField(pojoObject, field);
                    } catch (IllegalAccessException e) {
                        log.error("handlePojo() - error during fields setting.", e);
                    }
                }
            }
        } else {
            if (hasAnyFieldValueAnnotated(pojoObject) || hasAnyMethodValueAnnotated(pojoObject)) {
                log.debug("handleHuman() - The class {} has no @Entity annotation, \n" +
                                "but some field or method has @Value annotation, throwing IllegalStateException.",
                        pojoObject.getClass().getSimpleName());
                throw new IllegalStateException("No @Entity but found @Value.");
            }
        }
    }

    /**
     * Checks if the passed pojoObject's class annotated with @Entity annotation.
     *
     * @param pojoObject pojo object.
     * @return false if the class of pojoObject not annotated with @Entity.
     */
    public boolean hasEntityAnnotation(Object pojoObject) {
        log.debug("hasEntityAnnotation() - starting to check if the class {} has annotation @Entity.",
                pojoObject.getClass().getSimpleName());
        boolean result = false;
        Class<?> aClass = pojoObject.getClass();
        Entity entityAnnotation = aClass.getAnnotation(Entity.class);
        if (Objects.nonNull(entityAnnotation)) {
            result = true;
        }
        log.debug("hasEntityAnnotation() - result of this check - {}.", result);
        return result;
    }

    /**
     * checks if the passed object's class has any @Value annotation on its fields.
     *
     * @param pojoObject pojo object.
     * @return false if there is no fields, annotated with @Value, , else returns true.
     */
    public boolean hasAnyFieldValueAnnotated(Object pojoObject) {
        log.debug("hasFieldsValueAnnotation() - starting to check if fields of the class {} has annotation @Value.",
                pojoObject.getClass().getSimpleName());
        Field[] declaredFields = pojoObject.getClass().getDeclaredFields();
        boolean result = hasValueAnnotation(declaredFields);
        log.debug("hasFieldsValueAnnotation() - result of this check - {}.", result);
        return result;
    }

    /**
     * Checks if the passed object has any @Value annotation on its methods.
     *
     * @param pojoObject pojo object.
     * @return false if there is no setters, annotated with @Value, else returns true.
     */
    public boolean hasAnyMethodValueAnnotated(Object pojoObject) {
        log.debug("hasMethodsValueAnnotation() - starting to check if methods of the class {} has annotation @Value.",
                pojoObject.getClass().getSimpleName());
        Method[] declaredMethods = pojoObject.getClass().getDeclaredMethods();
        boolean result = hasValueAnnotation(declaredMethods);
        log.debug("hasMethodsValueAnnotation() - result of this check - {}.", result);
        return result;
    }

    /**
     * Trying to set the value from the setter's annotation to the field.
     *
     * @param field      the field of the pojoObject.
     * @param pojoObject instance of a POJO class.
     * @return false if setting was unsuccessful, else returns true.
     */
    private boolean setFromSetterAnnotation(Field field, Object pojoObject) {
        log.debug("setFromSetterAnnotation() - start to search setter to field {}.", field.getName());
        log.trace("setFromSetterAnnotation() - field type is {}.", field.getType());
        boolean result = false;
        try {
            String setterName = makeSetterName(field);
            log.debug("setFromSetterAnnotation() - searching for annotated @Value method {}.", setterName);
            Method setterMethod = pojoObject.getClass().getDeclaredMethod(setterName, field.getType());
            Value setterAnnotation = setterMethod.getAnnotation(Value.class);
            if (Objects.nonNull(setterAnnotation)) {
                result = setFieldFromAnnotationInstance(field, pojoObject, setterAnnotation);
            }
        } catch (NoSuchMethodException e) {
            log.warn("setFromSetterAnnotation() - no annotated @Value setter found!", e);
        }
        log.trace("setFromSetterAnnotation() - success: {}.", result);
        return result;
    }

    /**
     * Trying to set the field with value or using the path of @Value instance.
     *
     * @param field      the field of the pojoObject.
     * @param pojoObject instance of a POJO class
     * @param annotation instance of @Value annotation that contains the value or the path.
     * @return true if setting was successful.
     */
    private boolean setFieldFromAnnotationInstance(Field field, Object pojoObject, Value annotation) {
        if (Objects.nonNull(pathValueMap) && isPathPassed(annotation)) {
            String pathKey = annotation.path().toLowerCase(Locale.ROOT);
            String value = pathValueMap.get(pathKey);
            if (Objects.isNull(value)) {
                return false;
            }
            return trySetValue(field, pojoObject, value);
        } else {
            String value = annotation.value();
            return trySetValue(field, pojoObject, value);
        }
    }

    /**
     * Checks if the annotation has path filled.
     *
     * @param annotation @Value annotation to check.
     * @return true if "path" field filled.
     */
    private boolean isPathPassed(Value annotation) {
        return annotation.path().length() > 0;
    }

    /**
     * Trying to set the value from the field's annotation to the field.
     *
     * @param field      the field of the pojoObject.
     * @param pojoObject instance of a POJO class.
     * @return false if setting was unsuccessful, else returns true.
     */
    private boolean setFromFieldAnnotation(Field field, Object pojoObject) {
        log.debug("setFromFieldAnnotation() - start to search @Value on field {}", field.getName());
        log.trace("setFromFieldAnnotation() - field type is {}", field.getType());
        boolean result = false;
        Value fieldAnnotation = field.getAnnotation(Value.class);
        if (Objects.nonNull(fieldAnnotation)) {
            result = setFieldFromAnnotationInstance(field, pojoObject, fieldAnnotation);
        }
        log.debug("setFromFieldAnnotation() - setting success: {}, field's name: {}", result, field.getName());
        return result;
    }

    /**
     * Creates a setter name from the field name.
     *
     * @param field the field to its setter name create.
     * @return setter name in the form of "setFieldName".
     */
    private String makeSetterName(Field field) {
        String fieldName = field.getName();
        StringBuilder builder = new StringBuilder(fieldName);
        builder.setCharAt(0, Character.toUpperCase(fieldName.charAt(0)));
        return "set" + builder.toString();
    }

    /**
     * Sets the field with int type by the value from its @Value annotation.
     *
     * @param field      the field to set its value.
     * @param pojoObject instance of a POJO class.
     * @param value      passed fields's @Value annotation.
     * @throws IllegalAccessException when unable to set field's value.
     */
    private void setInt(Field field, Object pojoObject, String value) throws IllegalAccessException {
        field.setAccessible(true);
        log.trace("setInt() - annotation.value() = {}", value);
        int intValue = Integer.parseInt(value);
        field.set(pojoObject, intValue);
    }

    /**
     * Sets the field with String type by the value from its @Value annotation.
     *
     * @param field      the field to set its value.
     * @param pojoObject instance of a POJO class.
     * @param value      passed fields's @Value annotation.
     * @throws IllegalAccessException when unable to set field's value.
     */
    private void setString(Field field, Object pojoObject, String value) throws IllegalAccessException {
        field.setAccessible((true));
        log.trace("setString() - annotation.value() = {}", value);
        field.set(pojoObject, value);
    }

    /**
     * trying to set the field by value from its @Value annotation.
     *
     * @param field      the field to set its value.
     * @param pojoObject instance of a POJO class.
     * @param value      the @Value annotation.
     * @return false if setting was unsuccessful, else true.
     */
    private boolean trySetValue(Field field, Object pojoObject, String value) {
        boolean result = false;
        try {
            if (field.getType().equals(Integer.class) || field.getType().equals(int.class)) {
                setInt(field, pojoObject, value);
                result = true;
            } else if (field.getType().equals(String.class)) {
                setString(field, pojoObject, value);
                result = true;
            }
        } catch (IllegalAccessException e) {
            log.warn("trySetValue() - fail to set field from field's @Value.");
        } catch (NumberFormatException e) {
            log.warn("trySetValue() - wrong type of the value in @Value.", e);
        }
        return result;
    }

    /**
     * Trying to set the value from @Value annotation's default value.
     *
     * @param field      the field to set its value.
     * @param pojoObject instance of a POJO class.
     * @throws IllegalAccessException when unable to set field's value.
     */
    private void setFromDefault(Field field, Object pojoObject) throws IllegalAccessException {
        field.setAccessible(true);
        if (field.getType().equals(int.class)) {
            field.set(pojoObject, INT_DEFAULT);
        } else if (field.getType().equals(String.class)) {
            field.set(pojoObject, STRING_DEFAULT);
        } else {
            throw new TypeUnsupportedException("Received unsupported type of the value.");
        }
        log.debug("setFromDefault() - Setting value from default.");
    }

    /**
     * Sets field with, using @Value annotations.
     *
     * @param pojoObject instance of a POJO class.
     * @param field      the field to set its value.
     * @throws IllegalAccessException when unable to access the field.
     */
    private void setField(Object pojoObject, Field field) throws IllegalAccessException {
        if (setFromSetterAnnotation(field, pojoObject)) {
            log.trace("handleHuman() - setFieldFromSetter() = true, field: {}.", field.getName());
        } else if (setFromFieldAnnotation(field, pojoObject)) {
            log.trace("handleHuman() - setFromFieldAnnotation() = true, field: {}.", field.getName());
        } else {
            setFromDefault(field, pojoObject);
        }
    }

    /**
     * checks if passed AnnotatedElements have the @Value annotation.
     *
     * @param elements AnnotatedElement objects.
     * @return true if at least one element has @Value annotation, else false.
     */
    private boolean hasValueAnnotation(AnnotatedElement[] elements) {
        for (AnnotatedElement element : elements) {
            Value annotation = element.getAnnotation(Value.class);
            if (Objects.nonNull(annotation)) {
                return true;
            }
        }
        return false;
    }
}
