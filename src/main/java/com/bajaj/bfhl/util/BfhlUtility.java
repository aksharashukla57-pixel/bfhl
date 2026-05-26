package com.bajaj.bfhl.util;

import java.math.BigInteger;
import java.util.List;

/**
 * Utility helper class for input classification, summation, and custom string mutation.
 * 
 * Provides static helper methods following single responsibility and utility patterns.
 */
public final class BfhlUtility {

    // Prevent instantiation
    private BfhlUtility() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Detects if a given string is a valid integer.
     * Supports negative numbers and arbitrarily large numbers.
     * 
     * @param str the string to inspect
     * @return true if the string represents an integer, false otherwise
     */
    public static boolean isNumeric(String str) {
        if (str == null || str.trim().isEmpty()) {
            return false;
        }
        return str.matches("^-?\\d+$");
    }

    /**
     * Determines if a numeric string represents an even integer.
     * Uses BigInteger testBit to securely and efficiently determine parity without overflow.
     * 
     * @param numericStr the numeric string (must be validated with isNumeric beforehand)
     * @return true if even, false if odd
     */
    public static boolean isEven(String numericStr) {
        BigInteger value = new BigInteger(numericStr);
        return !value.testBit(0); // If bit 0 is 0, the number is even
    }

    /**
     * Detects if a given string consists entirely of alphabetical characters.
     * 
     * @param str the string to inspect
     * @return true if the string is purely alphabetic, false otherwise
     */
    public static boolean isAlphabet(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        return str.matches("^[a-zA-Z]+$");
    }

    /**
     * Computes the sum of all numeric elements in the list.
     * Uses BigInteger to prevent integer overflow for extreme values.
     * 
     * @param numbers the list of numeric strings
     * @return the sum as a string, or "0" if the list is empty
     */
    public static String calculateSum(List<String> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            return "0";
        }
        BigInteger sum = BigInteger.ZERO;
        for (String num : numbers) {
            sum = sum.add(new BigInteger(num));
        }
        return sum.toString();
    }

    /**
     * Generates a custom concatenated string based on the following rules:
     * 1. Extract all alphabetic characters from the alphabet strings.
     * 2. Reverse the complete character sequence.
     * 3. Apply alternating caps: 1st char Uppercase, 2nd lowercase, 3rd Uppercase, etc.
     * 
     * @param alphabets the list of classified alphabet strings
     * @return the mutated string, or empty string if no alphabets are present
     */
    public static String generateConcatString(List<String> alphabets) {
        if (alphabets == null || alphabets.isEmpty()) {
            return "";
        }

        // 1. Extract all alphabetic characters from all strings in the list
        StringBuilder rawExtracted = new StringBuilder();
        for (String element : alphabets) {
            for (char ch : element.toCharArray()) {
                if (Character.isLetter(ch)) {
                    rawExtracted.append(ch);
                }
            }
        }

        // 2. Reverse the complete sequence
        String reversed = rawExtracted.reverse().toString();

        // 3. Apply alternating caps (Index 0 -> Upper, Index 1 -> Lower, Index 2 -> Upper, etc.)
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < reversed.length(); i++) {
            char ch = reversed.charAt(i);
            if (i % 2 == 0) {
                result.append(Character.toUpperCase(ch));
            } else {
                result.append(Character.toLowerCase(ch));
            }
        }

        return result.toString();
    }
}
