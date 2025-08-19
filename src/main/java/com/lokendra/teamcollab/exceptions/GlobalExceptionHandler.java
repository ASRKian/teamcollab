package com.lokendra.teamcollab.exceptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.lokendra.teamcollab.dto.ErrorDto;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConflictEmailException.class)
    public ResponseEntity<ErrorDto> handleConflictEmail(ConflictEmailException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorDto(ex.getMessage()));
    }

    // @ExceptionHandler(MethodArgumentNotValidException.class)
    // public ResponseEntity<Map<String, String>>
    // handleValidationExceptions(MethodArgumentNotValidException ex) {
    // Map<String, String> errors = new HashMap<>();

    // ex.getBindingResult().getAllErrors().forEach(error -> {
    // String fieldName = ((FieldError) error).getField();
    // String errorMessage = error.getDefaultMessage();
    // errors.put(fieldName, errorMessage);
    // });

    // return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    // }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex) {

        Map<String, Object> response = new HashMap<>();
        response.put("error", "Validation failed");
        response.put("status", HttpStatus.BAD_REQUEST.value());

        // // Extract field errors
        // Map<String, String> fieldErrors = ex.getBindingResult()
        // .getFieldErrors()
        // .stream()
        // .collect(Collectors.toMap(
        // err -> err.getField(),
        // err -> err.getDefaultMessage(),
        // (msg1, msg2) -> msg1 // if duplicate keys
        // ));

        // // Extract global (class-level) errors (like your @AtLeastOneField)
        // var globalErrors = ex.getBindingResult()
        // .getGlobalErrors()
        // .stream()
        // .map(err -> err.getDefaultMessage())
        // .collect(Collectors.toList());

        // response.put("fieldErrors", fieldErrors);
        // response.put("globalErrors", globalErrors);
        List<String> errors = new ArrayList<>();

        // Field-level errors
        ex.getBindingResult().getFieldErrors()
                .forEach(err -> errors.add(err.getField() + ": " + err.getDefaultMessage()));

        // Global/class-level errors
        ex.getBindingResult().getGlobalErrors()
                .forEach(err -> errors.add(err.getDefaultMessage()));

        response.put("errors", errors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TeamNotFoundException.class)
    public ResponseEntity<ErrorDto> handleTeamNotFoundException(TeamNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorDto(ex.getMessage()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Void> handleBadCredentialsException(BadCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(NoHandlerFoundException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("status", 404);
        error.put("error", "Not Found");
        error.put("message", "The requested route does not exist.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<Map<String, Object>> handleUnsupportedMediaType(HttpMediaTypeNotSupportedException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("status", 415);
        error.put("error", "Unsupported Media Type");
        error.put("message", "Content-Type not supported. Please use application/json.");
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(error);
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<ErrorDto> handleProjectNotFoundException(ProjectNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorDto("Invalid project id"));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorDto> handleUserNotFoundException(UsernameNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto(ex.getMessage()));
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorDto> handleTaskNotFoundException(TaskNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto("Task not found"));
    }

    // Missing or invalid JSON body
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleEmptyBody(HttpMessageNotReadableException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Invalid input: Please check your request body.");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorDto> handleInvalidRouteException(NoResourceFoundException ex) {
        System.out.println(ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorDto("invalid Route"));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorDto> handle(HttpRequestMethodNotSupportedException ex) {
        System.out.println(ex);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorDto("invalid http method"));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDto> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorDto(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleGeneric(Exception ex) {
        System.out.println(ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorDto("An unexpected error occurred"));
    }
}
