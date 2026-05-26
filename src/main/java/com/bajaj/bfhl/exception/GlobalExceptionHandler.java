package com.bajaj.bfhl.exception;

import com.bajaj.bfhl.dto.BfhlErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * Global REST Exception Handler.
 * Intercepts all controller exceptions to ensure the application always returns
 * a standardized JSON error response (containing is_success: false) and meaningful logs.
 * 
 * Written in standard Java without Lombok to support JDK 26 compiler profiles.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles custom BfhlException (validation or logical errors).
     */
    @ExceptionHandler(BfhlException.class)
    public ResponseEntity<BfhlErrorResponse> handleBfhlException(BfhlException ex) {
        log.error("BFHL Application Exception occurred: {}", ex.getMessage());
        BfhlErrorResponse error = BfhlErrorResponse.builder()
                .errorMessage(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    /**
     * Handles JSR-380 bean validation errors (e.g. null request body or missing data field).
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BfhlErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        String combinedErrors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));
        
        log.error("Validation error(s) occurred: {}", combinedErrors);
        BfhlErrorResponse error = BfhlErrorResponse.builder()
                .errorMessage("Validation failed: " + combinedErrors)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    /**
     * Handles malformed JSON payloads gracefully.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<BfhlErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        log.error("Malformed JSON request payload received", ex);
        BfhlErrorResponse error = BfhlErrorResponse.builder()
                .errorMessage("Malformed JSON request payload or invalid data type")
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    /**
     * Fallback for any unhandled standard runtime or checked exceptions.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BfhlErrorResponse> handleGeneralException(Exception ex) {
        log.error("An unexpected internal error occurred", ex);
        BfhlErrorResponse error = BfhlErrorResponse.builder()
                .errorMessage("An unexpected internal server error occurred. Please try again.")
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
