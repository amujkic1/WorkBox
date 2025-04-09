package com.example.demo.controller.advice;

import com.example.demo.exception.CheckInRecordNotFoundException;
import com.example.demo.exception.EmployeeBenefitNotFoundException;
import com.example.demo.exception.UserNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.nio.charset.CharacterCodingException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    //Metoda za kreiranje ErrorResponse objekta
    private Object createErrorResponse(String errorType, Object message) {
        return new ErrorResponse(errorType, message);
    }

    // @Validate or this method
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleModelConstraintViolationException(ConstraintViolationException ex, WebRequest request){
        List<String> errorMessages = ex.getConstraintViolations()
                .stream().
                map(violation -> violation.getMessage())
                .collect(Collectors.toList());

        return new ResponseEntity<>(createErrorResponse("validation", errorMessages), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(createErrorResponse("not_found", ex.getMessage()), HttpStatus.NOT_FOUND);
    }
/*
    @ExceptionHandler(EmployeeBenefitNotFoundException.class)
    public ResponseEntity<Object> handleEmployeeBenefitNotFoundException(EmployeeBenefitNotFoundException ex, WebRequest request){
        return new ResponseEntity<>(createErrorResponse("not_found", ex.getMessage()), HttpStatus.NOT_FOUND);
    }
*/
    /*
    @ExceptionHandler(CheckInRecordNotFoundException.class)
    public ResponseEntity<Object> handleCheckInRecordNotFoundException(CheckInRecordNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(createErrorResponse("not_found", ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmployeeBenefitNotFoundException.class)
    public ResponseEntity<Object> handleEmployeeBenefitNotFoundException(EmployeeBenefitNotFoundException ex, WebRequest request) {
        System.out.println("***********************");
        return new ResponseEntity<>(createErrorResponse("not_found", ex.getMessage()), HttpStatus.NOT_FOUND);
    }
    */
/*
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(createErrorResponse("server_error", ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
*/
}
