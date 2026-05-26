package com.bajaj.bfhl.service;

import com.bajaj.bfhl.dto.BfhlRequest;
import com.bajaj.bfhl.dto.BfhlResponse;

/**
 * Service interface defining the contract for processing BFHL requests.
 */
public interface BfhlService {

    /**
     * Processes the raw mixed data list and performs classifications, safety calculations,
     * and custom mutations matching business rules.
     * 
     * @param request the validated request payload
     * @return the fully populated success response payload
     */
    BfhlResponse processRequest(BfhlRequest request);
}
