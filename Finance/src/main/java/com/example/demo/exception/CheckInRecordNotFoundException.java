package com.example.demo.exception;

public class CheckInRecordNotFoundException extends RuntimeException{
    public CheckInRecordNotFoundException(Integer id) {
        super("Could not find check in record "+id);
    }
}
