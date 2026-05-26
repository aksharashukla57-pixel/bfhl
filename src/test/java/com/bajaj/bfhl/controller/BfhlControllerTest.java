package com.bajaj.bfhl.controller;

import com.bajaj.bfhl.dto.BfhlRequest;
import com.bajaj.bfhl.dto.BfhlResponse;
import com.bajaj.bfhl.service.BfhlService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BfhlController.class)
class BfhlControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BfhlService bfhlService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /bfhl - Success scenario")
    void testPostBfhlSuccess() throws Exception {
        BfhlRequest request = BfhlRequest.builder()
                .data(Arrays.asList("a", "1", "334"))
                .build();

        BfhlResponse response = BfhlResponse.builder()
                .isSuccess(true)
                .userId("john_doe_17091999")
                .email("john.doe@example.com")
                .rollNumber("ROLL_123")
                .oddNumbers(Collections.singletonList("1"))
                .evenNumbers(Collections.singletonList("334"))
                .alphabets(Collections.singletonList("A"))
                .specialCharacters(Collections.emptyList())
                .sum("335")
                .concatString("A")
                .build();

        Mockito.when(bfhlService.processRequest(any(BfhlRequest.class))).thenReturn(response);

        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success", is(true)))
                .andExpect(jsonPath("$.user_id", is("john_doe_17091999")))
                .andExpect(jsonPath("$.email", is("john.doe@example.com")))
                .andExpect(jsonPath("$.roll_number", is("ROLL_123")))
                .andExpect(jsonPath("$.odd_numbers[0]", is("1")))
                .andExpect(jsonPath("$.even_numbers[0]", is("334")))
                .andExpect(jsonPath("$.alphabets[0]", is("A")))
                .andExpect(jsonPath("$.sum", is("335")))
                .andExpect(jsonPath("$.concat_string", is("A")));
    }

    @Test
    @DisplayName("POST /bfhl - Validation fail on null data field")
    void testPostBfhlValidationFailure() throws Exception {
        BfhlRequest request = BfhlRequest.builder()
                .data(null) // Validation annotation @NotNull should fail
                .build();

        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.is_success", is(false)))
                .andExpect(jsonPath("$.error_message").exists());
    }

    @Test
    @DisplayName("POST /bfhl - Graceful handling of malformed JSON payload")
    void testPostBfhlMalformedJson() throws Exception {
        String malformedJson = "{ \"data\": [\"a\", \"b\" "; // missing closing bracket/brace

        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(malformedJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.is_success", is(false)))
                .andExpect(jsonPath("$.error_message", is("Malformed JSON request payload or invalid data type")));
    }
}
