package com.fct.visitation.repositories;

import com.fct.visitation.models.entity.Facility;
import com.fct.visitation.models.entity.Officer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Officer entity operations
 */
@Repository
public interface OfficerRepository extends JpaRepository<Officer, Long> {
    
    /**
     * Find an officer by email
     * 
     * @param email The email to search for
     * @return Optional containing the officer if found
     */
    Optional<Officer> findByEmail(String email);
    
    /**
     * Find an officer by username
     * 
     * @param username The username to search for
     * @return Optional containing the officer if found
     */
    Optional<Officer> findByUsername(String username);
    
    /**
     * Find an officer by staff ID
     * 
     * @param staffId The staff ID to search for
     * @return Optional containing the officer if found
     */
    Optional<Officer> findByStaffId(String staffId);
    
    /**
     * Find officers by first name or last name containing the search term
     * 
     * @param searchTerm The search term
     * @return List of officers with first name or last name containing the search term
     */
    @Query("SELECT o FROM Officer o WHERE " +
           "LOWER(o.firstName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(o.lastName) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Officer> findByNameContaining(@Param("searchTerm") String searchTerm);
    
    /**
     * Find officers by first name or last name containing the search term with pagination
     * 
     * @param searchTerm The search term
     * @param pageable Pagination information
     * @return Page of officers with first name or last name containing the search term
     */
    @Query("SELECT o FROM Officer o WHERE " +
           "LOWER(o.firstName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(o.lastName) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<Officer> findByNameContaining(@Param("searchTerm") String searchTerm, Pageable pageable);
    
    /**
     * Find officers by facility
     * 
     * @param facility The facility to search for
     * @return List of officers in the specified facility
     */
    List<Officer> findByFacility(Facility facility);
    
    /**
     * Find officers by facility with pagination
     * 
     * @param facility The facility to search for
     * @param pageable Pagination information
     * @return Page of officers in the specified facility
     */
    Page<Officer> findByFacility(Facility facility, Pageable pageable);
    
    /**
     * Find active officers
     * 
     * @return List of active officers
     */
    List<Officer> findByIsActiveTrue();
    
    /**
     * Find officers by department
     * 
     * @param department The department to search for
     * @return List of officers in the specified department
     */
    List<Officer> findByDepartmentContainingIgnoreCase(String department);
    
    /**
     * Find officers by title
     * 
     * @param title The title to search for
     * @return List of officers with the specified title
     */
    List<Officer> findByTitleContainingIgnoreCase(String title);
    
    /**
     * Find officers by availability status
     * 
     * @param availabilityStatus The availability status to search for
     * @return List of officers with the specified availability status
     */
    List<Officer> findByAvailabilityStatus(String availabilityStatus);
    
    /**
     * Find officers by facility and availability status
     * 
     * @param facility The facility to search for
     * @param availabilityStatus The availability status to search for
     * @return List of officers in the specified facility with the specified availability status
     */
    List<Officer> findByFacilityAndAvailabilityStatus(Facility facility, String availabilityStatus);
    
    /**
     * Find officers who require approval for visits
     * 
     * @return List of officers requiring approval for visits
     */
    List<Officer> findByRequiresApprovalTrue();
    
    /**
     * Count officers by facility
     * 
     * @param facility The facility to count for
     * @return Count of officers in the specified facility
     */
    long countByFacility(Facility facility);
    
    /**
     * Count active officers
     * 
     * @return Count of active officers
     */
    long countByIsActiveTrue();
    
    /**
     * Find officers with recent logins
     * 
     * @param since The date/time from which to search
     * @return List of officers who logged in since the specified date/time
     */
    List<Officer> findByLastLoginAfter(LocalDateTime since);
    
    /**
     * Find officers who have not logged in recently
     * 
     * @param cutoffDate The cutoff date/time
     * @return List of officers who have not logged in since the cutoff date/time
     */
    @Query("SELECT o FROM Officer o WHERE o.lastLogin IS NULL OR o.lastLogin < :cutoffDate")
    List<Officer> findByLastLoginBeforeOrNull(@Param("cutoffDate") LocalDateTime cutoffDate);
    
    /**
     * Get top officers with most visitor appointments
     * 
     * @param pageable Pagination information
     * @return List of officers ordered by visitor count
     */
    @Query("SELECT o, COUNT(v) as visitorCount FROM Officer o " +
           "JOIN o.visitors v " +
           "GROUP BY o.id " +
           "ORDER BY visitorCount DESC")
    List<Object[]> findTopOfficersByVisitorCount(Pageable pageable);
}