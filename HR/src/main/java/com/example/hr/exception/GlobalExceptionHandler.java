package com.example.hr.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> messages = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            messages.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return new ResponseEntity<>(Map.of(
                "error", "validation",
                "messages", messages
        ), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            ApplicationNotFoundException.class,
            OpeningNotFoundException.class,
            RecordNotFoundException.class,
            RequestNotFoundException.class
    })
    public ResponseEntity<ErrorResponse> handleNotFound(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("not_found", ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String message = "Nevažeći format ID parametra. Očekivan je broj, ali je primljeno: " + ex.getValue();
        return new ResponseEntity<>(new ErrorResponse("invalid_format", message), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingParam(MissingServletRequestParameterException ex) {
        String message = "Nedostaje obavezni parametar: " + ex.getParameterName();
        return new ResponseEntity<>(new ErrorResponse("missing_parameter", message), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        return new ResponseEntity<>(
                new ErrorResponse("server_error", ex.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}