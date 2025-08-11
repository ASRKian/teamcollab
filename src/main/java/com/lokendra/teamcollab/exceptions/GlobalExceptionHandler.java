package com.lokendra.teamcollab.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.lokendra.teamcollab.dto.ErrorDto;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConflictEmailException.class)
    public ResponseEntity<ErrorDto> handleConflictEmail(ConflictEmailException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorDto(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // Missing or invalid JSON body
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleEmptyBody(HttpMessageNotReadableException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Request body is missing or invalid JSON");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleGeneric(Exception ex) {
        System.out.println(ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorDto("An unexpected error occurred"));
    }
}
