package com.igushkin.homeworks.lesson9.exceptions;

/**
 * Signals that a method received a field with unsupported type.
 */
public class TypeUnsupportedException extends RuntimeException{
    public TypeUnsupportedException (String s) {
        super(s);
    }
}
