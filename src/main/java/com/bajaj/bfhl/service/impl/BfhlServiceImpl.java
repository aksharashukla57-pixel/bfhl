package com.bajaj.bfhl.service.impl;

import com.bajaj.bfhl.dto.BfhlRequest;
import com.bajaj.bfhl.dto.BfhlResponse;
import com.bajaj.bfhl.service.BfhlService;
import com.bajaj.bfhl.util.BfhlUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Enterprise service implementation of BfhlService.
 * Handles configuration mapping, categorization of mixed tokens, arithmetic summation,
 * and alphabetical mutations based on the challenge business rules.
 * 
 * Written in standard Java without Lombok to support JDK 26 compiler profiles.
 */
@Service
public class BfhlServiceImpl implements BfhlService {

    private static final Logger log = LoggerFactory.getLogger(BfhlServiceImpl.class);

    private final String userId;
    private final String email;
    private final String rollNumber;

    /**
     * Constructs BfhlServiceImpl by binding student details from application properties.
     */
    public BfhlServiceImpl(
            @Value("${bfhl.user.full-name}") String fullName,
            @Value("${bfhl.user.dob}") String dob,
            @Value("${bfhl.user.email}") String email,
            @Value("${bfhl.user.roll-number}") String rollNumber) {
        
        // Clean full name and combine with DOB to format user_id as <full_name_in_lowercase_ddmmyyyy>
        String cleanFullName = fullName.trim().toLowerCase().replaceAll("\\s+", "_");
        this.userId = cleanFullName + "_" + dob.trim();
        this.email = email.trim();
        this.rollNumber = rollNumber.trim();
        
        log.info("Initialized BFHL Service with User ID: {}, Email: {}, Roll Number: {}", 
                this.userId, this.email, this.rollNumber);
    }

    @Override
    public BfhlResponse processRequest(BfhlRequest request) {
        log.debug("Processing request with payload size: {}", request.getData().size());

        List<String> rawData = request.getData();
        List<String> oddNumbers = new ArrayList<>();
        List<String> evenNumbers = new ArrayList<>();
        List<String> alphabets = new ArrayList<>();
        List<String> specialCharacters = new ArrayList<>();
        List<String> allNumbers = new ArrayList<>();

        for (String element : rawData) {
            if (element == null) {
                continue;
            }
            
            // Classification using BfhlUtility rules
            if (BfhlUtility.isNumeric(element)) {
                allNumbers.add(element);
                if (BfhlUtility.isEven(element)) {
                    evenNumbers.add(element);
                } else {
                    oddNumbers.add(element);
                }
            } else if (BfhlUtility.isAlphabet(element)) {
                // Alphabet strings must be converted to uppercase
                alphabets.add(element.toUpperCase());
            } else {
                specialCharacters.add(element);
            }
        }

        // Calculations & String Mutations
        String sum = BfhlUtility.calculateSum(allNumbers);
        String concatString = BfhlUtility.generateConcatString(alphabets);

        log.debug("Processing completed. Sum: {}, Concat String: {}", sum, concatString);

        return BfhlResponse.builder()
                .isSuccess(true)
                .userId(userId)
                .email(email)
                .rollNumber(rollNumber)
                .oddNumbers(oddNumbers)
                .evenNumbers(evenNumbers)
                .alphabets(alphabets)
                .specialCharacters(specialCharacters)
                .sum(sum)
                .concatString(concatString)
                .build();
    }
}
