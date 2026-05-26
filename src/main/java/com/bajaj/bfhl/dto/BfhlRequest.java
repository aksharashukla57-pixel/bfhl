package com.bajaj.bfhl.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
 * Data Transfer Object representing the incoming BFHL request.
 * Contains the list of mixed values that need to be processed.
 * 
 * Written in standard Java without Lombok to support JDK 26 compiler profiles.
 */
public class BfhlRequest {

    @NotNull(message = "The 'data' field is required and cannot be null")
    private List<String> data;

    // No-arg constructor
    public BfhlRequest() {
    }

    // All-arg constructor
    public BfhlRequest(List<String> data) {
        this.data = data;
    }

    // Getter
    public List<String> getData() {
        return data;
    }

    // Setter
    public void setData(List<String> data) {
        this.data = data;
    }

    // Custom Builder Pattern
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<String> data;

        public Builder data(List<String> data) {
            this.data = data;
            return this;
        }

        public BfhlRequest build() {
            return new BfhlRequest(data);
        }
    }
}
