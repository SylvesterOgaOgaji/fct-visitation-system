package com.fct.visitation.repositories;

import com.fct.visitation.models.entity.NinVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for NinVerification entity operations
 */
@Repository
public interface NinVerificationRepository extends JpaRepository<NinVerification, Long> {
    
    /**
     * Find a NIN verification by the NIN
     * 
     * @param nin The National Identification Number
     * @return The NinVerification record if found, or null
     */
    NinVerification findByNin(String nin);
    
    /**
     * Find a NIN verification by NIN and check if it's valid
     * 
     * @param nin The National Identification Number
     * @param isValid Whether the verification is valid
     * @return The NinVerification record if found, or null
     */
    NinVerification findByNinAndIsValid(String nin, Boolean isValid);
    
    /**
     * Find valid NIN verifications that haven't expired yet
     * 
     * @param currentDateTime The current date and time
     * @return List of valid NIN verifications
     */
    List<NinVerification> findByIsValidTrueAndExpiryDateAfter(LocalDateTime currentDateTime);
    
    /**
     * Find NIN verifications by first name and last name
     * 
     * @param firstName The first name to search for
     * @param lastName The last name to search for
     * @return List of matching NIN verifications
     */
    List<NinVerification> findByFirstNameIgnoreCaseAndLastNameIgnoreCase(String firstName, String lastName);
    
    /**
     * Find NIN verifications that are about to expire
     * 
     * @param startDate Start of date range to check
     * @param endDate End of date range to check
     * @return List of NIN verifications expiring in the date range
     */
    List<NinVerification> findByExpiryDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * Count the number of valid NIN verifications
     * 
     * @return Count of valid verifications
     */
    long countByIsValidTrue();
    
    /**
     * Delete expired NIN verifications
     * 
     * @param expiryDate Date before which records are considered expired
     * @return Number of records deleted
     */
    long deleteByExpiryDateBefore(LocalDateTime expiryDate);
}