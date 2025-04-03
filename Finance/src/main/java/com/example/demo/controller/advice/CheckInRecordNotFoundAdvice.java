package com.example.demo.controller.advice;

import com.example.demo.exception.CheckInRecordNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CheckInRecordNotFoundAdvice {

    @ExceptionHandler(CheckInRecordNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String checkInRecordNotFoundHandler(CheckInRecordNotFoundException ex){
        return ex.getMessage();

    }
}
