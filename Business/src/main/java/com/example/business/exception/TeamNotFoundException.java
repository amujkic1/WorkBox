package com.example.business.exception;

public class TeamNotFoundException extends RuntimeException{
    public TeamNotFoundException(Integer id) {
        super("Could not find team "+id);
    }
}
