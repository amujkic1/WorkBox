package com.example.hr.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OpeningNotFoundException extends RuntimeException {
    public OpeningNotFoundException(Integer id) {
        super("Opening with ID " + id + " not found.");
    }
}
