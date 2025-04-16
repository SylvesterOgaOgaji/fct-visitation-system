package com.fct.visitation.repositories;

import com.fct.visitation.models.entity.PurposeOfVisit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for PurposeOfVisit entity operations
 */
@Repository
public interface PurposeOfVisitRepository extends JpaRepository<PurposeOfVisit, Long> {
    
    /**
     * Find a purpose of visit by code
     * 
     * @param code The code to search for
     * @return Optional containing the purpose of visit if found
     */
    Optional<PurposeOfVisit> findByCode(String code);
    
    /**
     * Find purposes of visit by description containing the search term
     * 
     * @param searchTerm The search term
     * @return List of purposes of visit with descriptions containing the search term
     */
    List<PurposeOfVisit> findByDescriptionContainingIgnoreCase(String searchTerm);
    
    /**
     * Find active purposes of visit
     * 
     * @return List of active purposes of visit
     */
    List<PurposeOfVisit> findByIsActiveTrue();
    
    /**
     * Find inactive purposes of visit
     * 
     * @return List of inactive purposes of visit
     */
    List<PurposeOfVisit> findByIsActiveFalse();
    
    /**
     * Find purposes of visit that require approval
     * 
     * @return List of purposes of visit requiring approval
     */
    List<PurposeOfVisit> findByRequiresApprovalTrue();
    
    /**
     * Find purposes of visit that require ID verification
     * 
     * @return List of purposes of visit requiring ID verification
     */
    List<PurposeOfVisit> findByRequiresIdVerificationTrue();
    
    /**
     * Find purposes of visit with duration less than or equal to specified minutes
     * 
     * @param minutes Maximum duration in minutes
     * @return List of purposes of visit with duration less than or equal to specified minutes
     */
    List<PurposeOfVisit> findByMaxDurationMinutesLessThanEqual(Integer minutes);
    
    /**
     * Find purposes of visit with duration greater than specified minutes
     * 
     * @param minutes Minimum duration in minutes
     * @return List of purposes of visit with duration greater than specified minutes
     */
    List<PurposeOfVisit> findByMaxDurationMinutesGreaterThan(Integer minutes);
    
    /**
     * Find purposes of visit that don't require approval or ID verification
     * 
     * @return List of purposes of visit not requiring approval or ID verification
     */
    List<PurposeOfVisit> findByRequiresApprovalFalseAndRequiresIdVerificationFalse();
    
    /**
     * Count active purposes of visit
     * 
     * @return Count of active purposes of visit
     */
    long countByIsActiveTrue();
}