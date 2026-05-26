package com.bajaj.bfhl.controller;

import com.bajaj.bfhl.dto.BfhlRequest;
import com.bajaj.bfhl.dto.BfhlResponse;
import com.bajaj.bfhl.service.BfhlService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller exposing REST endpoints for the BFHL challenge.
 * Exposes a POST endpoint to process token arrays.
 * 
 * Written in standard Java without Lombok to support JDK 26 compiler profiles.
 */
@RestController
@RequestMapping("/bfhl")
public class BfhlController {

    private static final Logger log = LoggerFactory.getLogger(BfhlController.class);

    private final BfhlService bfhlService;

    public BfhlController(BfhlService bfhlService) {
        this.bfhlService = bfhlService;
    }

    /**
     * POST /bfhl
     * Accepts a list of values, parses them, classifies them, and calculates metrics.
     * 
     * @param request the validated request payload
     * @return 200 OK along with processing results
     */
    @PostMapping
    public ResponseEntity<BfhlResponse> processData(@Valid @RequestBody BfhlRequest request) {
        log.info("Received POST request to process {} data elements", 
                request.getData() != null ? request.getData().size() : 0);
        
        BfhlResponse response = bfhlService.processRequest(request);
        return ResponseEntity.ok(response);
    }
}
