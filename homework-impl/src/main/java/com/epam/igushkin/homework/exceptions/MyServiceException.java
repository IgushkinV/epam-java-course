package com.epam.igushkin.homework.exceptions;

public class MyServiceException extends RuntimeException{

    public MyServiceException(String message) {
        super(message);
    }
}
