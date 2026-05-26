package com.bajaj.bfhl.exception;

/**
 * Custom application runtime exception for the BFHL challenge.
 * Used to signal validation failures or logical errors that require a custom 400 Bad Request response.
 */
public class BfhlException extends RuntimeException {

    public BfhlException(String message) {
        super(message);
    }

    public BfhlException(String message, Throwable cause) {
        super(message, cause);
    }
}
