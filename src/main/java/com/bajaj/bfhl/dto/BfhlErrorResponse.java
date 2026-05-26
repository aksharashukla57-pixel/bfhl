package com.bajaj.bfhl.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Standard Error Response DTO for the BFHL challenge API.
 * Ensures the API always returns a consistent structure, including 'is_success'.
 * 
 * Written in standard Java without Lombok to support JDK 26 compiler profiles.
 */
public class BfhlErrorResponse {

    @JsonProperty("is_success")
    private final boolean isSuccess = false;

    @JsonProperty("error_message")
    private String errorMessage;

    // No-arg constructor
    public BfhlErrorResponse() {
    }

    // All-arg constructor
    public BfhlErrorResponse(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    // Getters and Setters
    public boolean isSuccess() {
        return isSuccess;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    // Custom Builder Pattern
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String errorMessage;

        public Builder errorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
            return this;
        }

        public BfhlErrorResponse build() {
            return new BfhlErrorResponse(errorMessage);
        }
    }
}
