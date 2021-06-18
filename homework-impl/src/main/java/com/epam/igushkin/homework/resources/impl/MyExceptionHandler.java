package com.epam.igushkin.homework.resources.impl;

import com.epam.igushkin.homework.exceptions.NoEntityFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class MyExceptionHandler {

    @ExceptionHandler(NoEntityFoundException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "В базе не существует запись с указанным id.")
    public String handleException(NoEntityFoundException e) {
        log.warn("handleException() - Ошибка при обращении к базе - запрос несуществующей записи.", e);
        return e.getMessage();
    }
}
