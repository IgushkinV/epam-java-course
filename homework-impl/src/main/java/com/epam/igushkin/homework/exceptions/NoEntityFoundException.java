package com.epam.igushkin.homework.exceptions;

public class NoEntityFoundException extends RuntimeException{

    public NoEntityFoundException (String message) {
        super(message);
    }
}
