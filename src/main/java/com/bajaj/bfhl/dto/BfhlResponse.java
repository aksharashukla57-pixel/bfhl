package com.bajaj.bfhl.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Data Transfer Object representing the outgoing BFHL response.
 * Maps exact snake_case JSON keys required by the evaluation environment.
 * 
 * Written in standard Java without Lombok to support JDK 26 compiler profiles.
 */
public class BfhlResponse {

    @JsonProperty("is_success")
    private boolean isSuccess;

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("email")
    private String email;

    @JsonProperty("roll_number")
    private String rollNumber;

    @JsonProperty("odd_numbers")
    private List<String> oddNumbers;

    @JsonProperty("even_numbers")
    private List<String> evenNumbers;

    @JsonProperty("alphabets")
    private List<String> alphabets;

    @JsonProperty("special_characters")
    private List<String> specialCharacters;

    @JsonProperty("sum")
    private String sum;

    @JsonProperty("concat_string")
    private String concatString;

    // No-arg constructor
    public BfhlResponse() {
    }

    // All-arg constructor
    public BfhlResponse(boolean isSuccess, String userId, String email, String rollNumber,
                        List<String> oddNumbers, List<String> evenNumbers, List<String> alphabets,
                        List<String> specialCharacters, String sum, String concatString) {
        this.isSuccess = isSuccess;
        this.userId = userId;
        this.email = email;
        this.rollNumber = rollNumber;
        this.oddNumbers = oddNumbers;
        this.evenNumbers = evenNumbers;
        this.alphabets = alphabets;
        this.specialCharacters = specialCharacters;
        this.sum = sum;
        this.concatString = concatString;
    }

    // Getters and Setters
    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public List<String> getOddNumbers() {
        return oddNumbers;
    }

    public void setOddNumbers(List<String> oddNumbers) {
        this.oddNumbers = oddNumbers;
    }

    public List<String> getEvenNumbers() {
        return evenNumbers;
    }

    public void setEvenNumbers(List<String> evenNumbers) {
        this.evenNumbers = evenNumbers;
    }

    public List<String> getAlphabets() {
        return alphabets;
    }

    public void setAlphabets(List<String> alphabets) {
        this.alphabets = alphabets;
    }

    public List<String> getSpecialCharacters() {
        return specialCharacters;
    }

    public void setSpecialCharacters(List<String> specialCharacters) {
        this.specialCharacters = specialCharacters;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public String getConcatString() {
        return concatString;
    }

    public void setConcatString(String concatString) {
        this.concatString = concatString;
    }

    // Custom Builder Pattern
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private boolean isSuccess;
        private String userId;
        private String email;
        private String rollNumber;
        private List<String> oddNumbers;
        private List<String> evenNumbers;
        private List<String> alphabets;
        private List<String> specialCharacters;
        private String sum;
        private String concatString;

        public Builder isSuccess(boolean isSuccess) {
            this.isSuccess = isSuccess;
            return this;
        }

        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder rollNumber(String rollNumber) {
            this.rollNumber = rollNumber;
            return this;
        }

        public Builder oddNumbers(List<String> oddNumbers) {
            this.oddNumbers = oddNumbers;
            return this;
        }

        public Builder evenNumbers(List<String> evenNumbers) {
            this.evenNumbers = evenNumbers;
            return this;
        }

        public Builder alphabets(List<String> alphabets) {
            this.alphabets = alphabets;
            return this;
        }

        public Builder specialCharacters(List<String> specialCharacters) {
            this.specialCharacters = specialCharacters;
            return this;
        }

        public Builder sum(String sum) {
            this.sum = sum;
            return this;
        }

        public Builder concatString(String concatString) {
            this.concatString = concatString;
            return this;
        }

        public BfhlResponse build() {
            return new BfhlResponse(isSuccess, userId, email, rollNumber, oddNumbers, evenNumbers,
                    alphabets, specialCharacters, sum, concatString);
        }
    }
}
