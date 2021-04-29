package com.igushkin.homeworks.lesson9.exceptions;

/**
 * Signals that data in a file has unexpected format
 */
public class WrongDataFormatException extends RuntimeException{

    public WrongDataFormatException(String s){
        super(s);
    }
}
