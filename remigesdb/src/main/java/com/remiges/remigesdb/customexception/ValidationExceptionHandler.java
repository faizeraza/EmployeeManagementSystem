package com.remiges.remigesdb.customexception;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.remiges.remigesdb.dto.Response;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response<String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        Response<String> response = Response.failure(errorMessage, HttpStatus.BAD_REQUEST.value(), null);  // Returning errors as a string

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Response<String>> handleValidationExceptions(ConstraintViolationException ex) {
        String errorMessage = ex.getConstraintViolations()
                .stream()
                .map(violation -> violation.getMessage())
                .findFirst()
                .orElse("Validation error");
        Response<String> response = Response.failure(errorMessage, HttpStatus.BAD_REQUEST.value(), null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Response<String>> handleValidationExceptions(ExpiredJwtException ex) {
        String errorMessage = "The Token is Expired please generate token again !! ";
        Response<String> response = Response.failure(errorMessage, HttpStatus.BAD_REQUEST.value(), null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // @ExceptionHandler(HttpMessageNotReadableException.class)
    // public ResponseEntity<Response<String>> handleValidationExceptions(HttpMessageNotReadableException ex) {
    //     String errorMessage = ex.getMessage();
    //     Response<String> response = Response.failure(errorMessage, HttpStatus.BAD_REQUEST.value(), null);
    //     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    // }
    // @ExceptionHandler(MissingServletRequestParameterException.class)
    // public ResponseEntity<Response<String>> handleValidationExceptions(MissingServletRequestParameterException ex) {
    //     String errorMessage = ex.getMessage();
    //     Response<String> response = Response.failure(errorMessage, HttpStatus.BAD_REQUEST.value(), null);
    //     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    // }
}
