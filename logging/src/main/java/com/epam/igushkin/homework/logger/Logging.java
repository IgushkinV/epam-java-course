package com.epam.igushkin.homework.logger;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *  Примеяется к методам, для которых нужно вывести лог в консоль.
 *  Выводит название метода и входные аргументы в виде methodName([args)].
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Logging {
}
