package com.example.business.controller.advice;

import com.example.business.exception.TaskNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public class TaskNotFoundAdvice {
    @ExceptionHandler(TaskNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String teamNotFoundHandler(TaskNotFoundException ex){
        return ex.getMessage();
    }
}
