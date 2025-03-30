package com.example.business.exception;

public class ProjectNotFoundException extends RuntimeException{
    public ProjectNotFoundException(Integer id) {
        super("Could not find project "+id);
    }
}
