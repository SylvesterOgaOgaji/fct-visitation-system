package com.fct.visitation.services.interfaces;

import com.fct.visitation.models.entity.NinVerification;

public interface NinVerificationService {
    /**
     * Verifies a National Identification Number (NIN)
     * @param nin The NIN to verify
     * @param firstName The first name to validate against the NIN
     * @param lastName The last name to validate against the NIN
     * @return true if NIN is valid and matches the provided details, false otherwise
     */
    boolean verifyNin(String nin, String firstName, String lastName);
    
/**
     * Get the verification details for a NIN
     * @param nin The NIN to get details for
     * @return The verification details or null if not found
     */
    NinVerification getNinDetails(String nin);
}
