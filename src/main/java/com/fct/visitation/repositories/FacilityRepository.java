package com.fct.visitation.repositories;

import com.fct.visitation.models.entity.Facility;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Facility entity operations
 */
@Repository
public interface FacilityRepository extends JpaRepository<Facility, Long> {
    
    /**
     * Find a facility by its code
     * 
     * @param code The facility code to search for
     * @return Optional containing the facility if found
     */
    Optional<Facility> findByCode(String code);
    
    /**
     * Find facilities by name containing the search term (case-insensitive)
     * 
     * @param name The name search term
     * @return List of facilities with names containing the search term
     */
    List<Facility> findByNameContainingIgnoreCase(String name);
    
    /**
     * Find facilities by name containing the search term with pagination
     * 
     * @param name The name search term
     * @param pageable Pagination information
     * @return Page of facilities with names containing the search term
     */
    Page<Facility> findByNameContainingIgnoreCase(String name, Pageable pageable);
    
    /**
     * Find active facilities
     * 
     * @return List of active facilities
     */
    List<Facility> findByActiveTrue();
    
    /**
     * Find inactive facilities
     * 
     * @return List of inactive facilities
     */
    List<Facility> findByActiveFalse();
    
    /**
     * Find facilities that require approval for visits
     * 
     * @return List of facilities requiring approval
     */
    List<Facility> findByRequiresApprovalTrue();
    
    /**
     * Find facilities by address containing the search term
     * 
     * @param addressTerm The address search term
     * @return List of facilities with addresses containing the search term
     */
    List<Facility> findByAddressContainingIgnoreCase(String addressTerm);
    
    /**
     * Find facilities with geo-coordinates
     * 
     * @return List of facilities with latitude and longitude values
     */
    @Query("SELECT f FROM Facility f WHERE f.latitude IS NOT NULL AND f.longitude IS NOT NULL")
    List<Facility> findWithGeoCoordinates();
    
    /**
     * Count active facilities
     * 
     * @return Count of active facilities
     */
    long countByActiveTrue();
    
    /**
     * Count inactive facilities
     * 
     * @return Count of inactive facilities
     */
    long countByActiveFalse();
    
    /**
     * Find facilities with available parking
     * 
     * @return List of facilities with non-zero parking capacity
     */
    @Query("SELECT f FROM Facility f WHERE f.parkingCapacity > 0")
    List<Facility> findWithParking();
    
    /**
     * Find facilities with available parking sorted by capacity
     * 
     * @return List of facilities with non-zero parking capacity sorted by capacity
     */
    @Query("SELECT f FROM Facility f WHERE f.parkingCapacity > 0 ORDER BY f.parkingCapacity DESC")
    List<Facility> findWithParkingSortedByCapacity();
    
    /**
     * Find facilities with visitor capacity above a threshold
     * 
     * @param threshold The minimum visitor capacity
     * @return List of facilities with visitor capacity above the threshold
     */
    List<Facility> findByVisitorCapacityGreaterThanEqual(int threshold);
    
    /**
     * Find facilities by email domain
     * 
     * @param emailDomain The email domain to search for
     * @return List of facilities with emails in the specified domain
     */
    @Query("SELECT f FROM Facility f WHERE f.email LIKE %:emailDomain")
    List<Facility> findByEmailDomain(String emailDomain);
}