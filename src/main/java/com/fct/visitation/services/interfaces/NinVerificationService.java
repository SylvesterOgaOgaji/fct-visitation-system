package com.fct.visitation.services.interfaces;

import com.fct.visitation.models.entity.NinVerification;

import java.util.Optional;

public interface NinVerificationService {
    /**
     * Verify NIN (National Identification Number)
     * @param nin National Identification Number
     * @return Optional of NinVerification
     */
    Optional<NinVerification> verifyNin(String nin);

    /**
     * Verify NIN with additional context
     * @param nin National Identification Number
     * @param contextId Context ID for verification
     * @return Optional of NinVerification
     */
    Optional<NinVerification> verifyNin(String nin, Long contextId);

    /**
     * Find NIN verification by ID
     * @param id Verification ID
     * @return Optional of NinVerification
     */
    Optional<NinVerification> findById(Long id);

    /**
     * Save NIN verification result
     * @param ninVerification NIN verification to save
     * @return Saved NIN verification
     */
    NinVerification save(NinVerification ninVerification);

    /**
     * Validate NIN details comprehensively
     * @param nin National Identification Number
     * @param firstName First name of the individual
     * @param lastName Last name of the individual
     * @return Optional of NinVerification with validation result
     */
    Optional<NinVerification> validateNinDetails(String nin, String firstName, String lastName);
}