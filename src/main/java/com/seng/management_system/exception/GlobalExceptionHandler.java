package com.seng.management_system.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.seng.management_system.apiResponse.ApiResponse;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<?> handleApiException(ApiException ex) {
        return ResponseEntity.badRequest().body(ApiResponse.fail(ex.getMessage()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDuplicate(DataIntegrityViolationException ex) {

        String message = "Duplicate value detected!";

        // Optional: detect specific field
        if (ex.getRootCause() != null && ex.getRootCause().getMessage() != null) {
            String rootMsg = ex.getRootCause().getMessage();
            return ResponseEntity.badRequest().body(ApiResponse.fail(rootMsg));
        }

        return ResponseEntity.badRequest().body(ApiResponse.fail(message));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", 400);
        response.put("error", "Bad Request");
        response.put("message", "Validation failed");
        response.put("path", request.getRequestURI());
        response.put("errors", errors);

        return ResponseEntity.badRequest().body(response);
    }
}
