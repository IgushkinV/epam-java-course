package com.epam.igushkin.homework.exceptions;

/**
 * Использовать, когда не удалось найти сущность в репозитории.
 */
public class NoEntityFoundException extends RuntimeException {

    public NoEntityFoundException (String message) {
        super(message);
    }
}
