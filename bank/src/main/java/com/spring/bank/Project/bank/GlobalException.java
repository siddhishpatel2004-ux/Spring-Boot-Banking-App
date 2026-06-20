package com.spring.bank.Project.bank;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(RuntimeException.class)
    public String RuntimeExceptionHandler(RuntimeException e){
        return e.getMessage();
    }
    @ExceptionHandler(Exception.class)
    public String ExceptionHandler(Exception e){
        return "Something went wrong !";
    }
}
