package com.bajaj.bfhl.service;

import com.bajaj.bfhl.dto.BfhlRequest;
import com.bajaj.bfhl.dto.BfhlResponse;
import com.bajaj.bfhl.service.impl.BfhlServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BfhlServiceTest {

    private BfhlService bfhlService;

    @BeforeEach
    void setUp() {
        // Construct Service manually using configuration constants for clean unit tests
        bfhlService = new BfhlServiceImpl(
                "john_doe",
                "17091999",
                "john.doe@example.com",
                "ROLL_123"
        );
    }

    @Test
    @DisplayName("Test mixed valid inputs (matching the challenge requirements)")
    void testMixedValidInput() {
        BfhlRequest request = BfhlRequest.builder()
                .data(Arrays.asList("a", "1", "334", "4", "R", "$"))
                .build();

        BfhlResponse response = bfhlService.processRequest(request);

        assertTrue(response.isSuccess());
        assertEquals("john_doe_17091999", response.getUserId());
        assertEquals("john.doe@example.com", response.getEmail());
        assertEquals("ROLL_123", response.getRollNumber());

        assertEquals(List.of("1"), response.getOddNumbers());
        assertEquals(Arrays.asList("334", "4"), response.getEvenNumbers());
        assertEquals(Arrays.asList("A", "R"), response.getAlphabets());
        assertEquals(List.of("$"), response.getSpecialCharacters());
        assertEquals("339", response.getSum());
        assertEquals("Ra", response.getConcatString());
    }

    @Test
    @DisplayName("Test complex alphabet concatenation and alternating caps rules")
    void testConcatStringComplexAlternatingCaps() {
        BfhlRequest request = BfhlRequest.builder()
                .data(Arrays.asList("A", "ABCD", "DOE"))
                .build();

        BfhlResponse response = bfhlService.processRequest(request);

        assertTrue(response.isSuccess());
        assertEquals(Arrays.asList("A", "ABCD", "DOE"), response.getAlphabets());
        assertEquals("EoDdCbAa", response.getConcatString()); // AABCDDOE reversed = EODDCBAA -> Alternating caps = EoDdCbAa
    }

    @Test
    @DisplayName("Test empty inputs")
    void testEmptyInput() {
        BfhlRequest request = BfhlRequest.builder()
                .data(Collections.emptyList())
                .build();

        BfhlResponse response = bfhlService.processRequest(request);

        assertTrue(response.isSuccess());
        assertTrue(response.getOddNumbers().isEmpty());
        assertTrue(response.getEvenNumbers().isEmpty());
        assertTrue(response.getAlphabets().isEmpty());
        assertTrue(response.getSpecialCharacters().isEmpty());
        assertEquals("0", response.getSum());
        assertEquals("", response.getConcatString());
    }

    @Test
    @DisplayName("Test only numeric inputs (odd and even)")
    void testOnlyNumericInputs() {
        BfhlRequest request = BfhlRequest.builder()
                .data(Arrays.asList("2", "4", "6", "1", "3", "5"))
                .build();

        BfhlResponse response = bfhlService.processRequest(request);

        assertEquals(Arrays.asList("1", "3", "5"), response.getOddNumbers());
        assertEquals(Arrays.asList("2", "4", "6"), response.getEvenNumbers());
        assertTrue(response.getAlphabets().isEmpty());
        assertTrue(response.getSpecialCharacters().isEmpty());
        assertEquals("21", response.getSum());
        assertEquals("", response.getConcatString());
    }

    @Test
    @DisplayName("Test extremely large numbers and negative numbers to verify BigInteger safety")
    void testLargeAndNegativeNumbers() {
        BfhlRequest request = BfhlRequest.builder()
                .data(Arrays.asList("999999999999999999", "-1", "-2"))
                .build();

        BfhlResponse response = bfhlService.processRequest(request);

        assertEquals(Arrays.asList("999999999999999999", "-1"), response.getOddNumbers());
        assertEquals(List.of("-2"), response.getEvenNumbers());
        // 999999999999999999 + (-1) + (-2) = 999999999999999996
        assertEquals("999999999999999996", response.getSum());
    }

    @Test
    @DisplayName("Test only special character inputs")
    void testOnlySpecialCharacters() {
        BfhlRequest request = BfhlRequest.builder()
                .data(Arrays.asList("$", "#", "@", "!"))
                .build();

        BfhlResponse response = bfhlService.processRequest(request);

        assertTrue(response.getOddNumbers().isEmpty());
        assertTrue(response.getEvenNumbers().isEmpty());
        assertTrue(response.getAlphabets().isEmpty());
        assertEquals(Arrays.asList("$", "#", "@", "!"), response.getSpecialCharacters());
        assertEquals("0", response.getSum());
        assertEquals("", response.getConcatString());
    }

    @Test
    @DisplayName("Test only alphabetic inputs")
    void testOnlyAlphabets() {
        BfhlRequest request = BfhlRequest.builder()
                .data(Arrays.asList("X", "y", "z"))
                .build();

        BfhlResponse response = bfhlService.processRequest(request);

        assertTrue(response.getOddNumbers().isEmpty());
        assertTrue(response.getEvenNumbers().isEmpty());
        assertEquals(Arrays.asList("X", "Y", "Z"), response.getAlphabets());
        assertEquals("0", response.getSum());
        assertEquals("ZyX", response.getConcatString()); // XYZ reversed -> ZYX -> Alternating caps -> ZyX
    }
}
