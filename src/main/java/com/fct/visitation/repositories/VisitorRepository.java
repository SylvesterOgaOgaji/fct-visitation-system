package com.fct.visitation.repositories;

import com.fct.visitation.models.entity.Facility;
import com.fct.visitation.models.entity.Officer;
import com.fct.visitation.models.entity.Visitor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for Visitor entity operations
 */
@Repository
public interface VisitorRepository extends JpaRepository<Visitor, Long> {
    
    /**
     * Find a visitor by QR code
     * 
     * @param qrCode The QR code to search for
     * @return The Visitor if found, or null
     */
    Visitor findByQrCode(String qrCode);
    
    /**
     * Find visitors by email
     * 
     * @param email The email address to search for
     * @return List of visitors with the specified email
     */
    List<Visitor> findByEmailIgnoreCase(String email);
    
    /**
     * Find visitors by National Identification Number (NIN)
     * 
     * @param nin The NIN to search for
     * @return List of visitors with the specified NIN
     */
    List<Visitor> findByNin(String nin);
    
    /**
     * Find visitors by status
     * 
     * @param status The status to search for
     * @return List of visitors with the specified status
     */
    List<Visitor> findByStatus(String status);
    
    /**
     * Find visitors by status with pagination
     * 
     * @param status The status to search for
     * @param pageable Pagination information
     * @return Page of visitors with the specified status
     */
    Page<Visitor> findByStatus(String status, Pageable pageable);
    
    /**
     * Find visitors for a specific facility
     * 
     * @param facility The facility to search for
     * @return List of visitors for the specified facility
     */
    List<Visitor> findByFacility(Facility facility);
    
    /**
     * Find visitors for a specific officer
     * 
     * @param officer The officer to search for
     * @return List of visitors for the specified officer
     */
    List<Visitor> findByOfficer(Officer officer);
    
    /**
     * Find visitors by appointment date range
     * 
     * @param startDate Start of date range
     * @param endDate End of date range
     * @return List of visitors with appointments in the specified date range
     */
    List<Visitor> findByAppointmentDatetimeBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * Find visitors with appointments today
     * 
     * @param startOfDay Start of the day
     * @param endOfDay End of the day
     * @return List of visitors with appointments today
     */
    List<Visitor> findByAppointmentDatetimeBetweenOrderByAppointmentDatetimeAsc(
            LocalDateTime startOfDay, LocalDateTime endOfDay);
    
    /**
     * Find visitors by car type
     * 
     * @param carType The car type to search for
     * @return List of visitors with the specified car type
     */
    List<Visitor> findByCarType(String carType);
    
    /**
     * Find visitors who registered within a date range
     * 
     * @param startDate Start of date range
     * @param endDate End of date range
     * @return List of visitors who registered in the specified date range
     */
    List<Visitor> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * Count visitors by facility and appointment date range
     * 
     * @param facility The facility to count for
     * @param startDate Start of date range
     * @param endDate End of date range
     * @return Count of visitors for the specified facility in the date range
     */
    long countByFacilityAndAppointmentDatetimeBetween(
            Facility facility, LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * Count visitors by status
     * 
     * @param status The status to count
     * @return Count of visitors with the specified status
     */
    long countByStatus(String status);
    
    /**
     * Find pending visitors with appointments in the future
     * 
     * @param status The status to search for (usually "Pending")
     * @param currentDateTime The current date and time
     * @return List of pending visitors with future appointments
     */
    List<Visitor> findByStatusAndAppointmentDatetimeAfter(String status, LocalDateTime currentDateTime);
    
    /**
     * Find visitors with vehicles
     * 
     * @return List of visitors who have registered with a vehicle
     */
    @Query("SELECT v FROM Visitor v WHERE v.carType != 'None' AND v.carType IS NOT NULL")
    List<Visitor> findVisitorsWithVehicles();
    
    /**
     * Search visitors by name or email
     * 
     * @param searchTerm The search term
     * @return List of visitors matching the search term
     */
    @Query("SELECT v FROM Visitor v WHERE " +
           "LOWER(v.firstName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(v.lastName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(v.email) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Visitor> searchByNameOrEmail(@Param("searchTerm") String searchTerm);
    
    /**
     * Count visitors by officer and date range
     * 
     * @param officer The officer to count for
     * @param startDate Start of date range
     * @param endDate End of date range
     * @return Count of visitors for the specified officer in the date range
     */
    long countByOfficerAndAppointmentDatetimeBetween(
            Officer officer, LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * Find visitors with a specific status at a specific facility
     * 
     * @param facility The facility to search for
     * @param status The status to search for
     * @return List of visitors at the specified facility with the specified status
     */
    List<Visitor> findByFacilityAndStatus(Facility facility, String status);
    
    /**
     * Count visitors with the same NIN within a date range
     * 
     * @param nin The NIN to count
     * @param startDate Start of date range
     * @param endDate End of date range
     * @return Count of visitors with the specified NIN in the date range
     */
    @Query("SELECT COUNT(v) FROM Visitor v WHERE v.nin = :nin AND v.appointmentDatetime BETWEEN :startDate AND :endDate")
    long countByNinAndDateRange(
            @Param("nin") String nin, 
            @Param("startDate") LocalDateTime startDate, 
            @Param("endDate") LocalDateTime endDate);
}