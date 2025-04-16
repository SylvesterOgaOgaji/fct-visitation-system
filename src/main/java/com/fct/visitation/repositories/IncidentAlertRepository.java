package com.fct.visitation.repositories;

import com.fct.visitation.models.entity.IncidentAlert;
import com.fct.visitation.models.entity.ResponseTeam;
import com.fct.visitation.models.entity.SecurityPersonnel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for IncidentAlert entity operations
 */
@Repository
public interface IncidentAlertRepository extends JpaRepository<IncidentAlert, Long> {
    
    /**
     * Find alerts by status
     * 
     * @param status The status to search for
     * @return List of alerts with the specified status
     */
    List<IncidentAlert> findByStatus(String status);
    
    /**
     * Find alerts by status with pagination
     * 
     * @param status The status to search for
     * @param pageable Pagination information
     * @return Page of alerts with the specified status
     */
    Page<IncidentAlert> findByStatus(String status, Pageable pageable);
    
    /**
     * Find alerts by severity
     * 
     * @param severity The severity to search for
     * @return List of alerts with the specified severity
     */
    List<IncidentAlert> findBySeverity(String severity);
    
    /**
     * Find alerts by severity with pagination
     * 
     * @param severity The severity to search for
     * @param pageable Pagination information
     * @return Page of alerts with the specified severity
     */
    Page<IncidentAlert> findBySeverity(String severity, Pageable pageable);
    
    /**
     * Find alerts by assigned response team
     * 
     * @param team The response team to search for
     * @return List of alerts assigned to the specified team
     */
    List<IncidentAlert> findByAssignedTeam(ResponseTeam team);
    
    /**
     * Find alerts by the security personnel who reported them
     * 
     * @param reporter The security personnel who reported the alerts
     * @return List of alerts reported by the specified personnel
     */
    List<IncidentAlert> findByReportedBy(SecurityPersonnel reporter);
    
    /**
     * Find alerts by the security personnel who resolved them
     * 
     * @param resolver The security personnel who resolved the alerts
     * @return List of alerts resolved by the specified personnel
     */
    List<IncidentAlert> findByResolvedBy(SecurityPersonnel resolver);
    
    /**
     * Find alerts by location
     * 
     * @param location The location to search for
     * @return List of alerts at the specified location
     */
    List<IncidentAlert> findByLocationContainingIgnoreCase(String location);
    
    /**
     * Find alerts reported within a date range
     * 
     * @param startDate Start of date range
     * @param endDate End of date range
     * @return List of alerts reported in the specified date range
     */
    List<IncidentAlert> findByReportedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * Find active high-severity alerts
     * 
     * @return List of active alerts with high severity
     */
    List<IncidentAlert> findByStatusAndSeverity(String status, String severity);
    
    /**
     * Find unassigned active alerts
     * 
     * @param status The status to search for (usually "ACTIVE")
     * @return List of active alerts not assigned to any team
     */
    List<IncidentAlert> findByStatusAndAssignedTeamIsNull(String status);
    
    /**
     * Find alerts resolved within a date range
     * 
     * @param startDate Start of date range
     * @param endDate End of date range
     * @return List of alerts resolved in the specified date range
     */
    List<IncidentAlert> findByResolvedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * Count alerts by status
     * 
     * @param status The status to count
     * @return Count of alerts with the specified status
     */
    long countByStatus(String status);
    
    /**
     * Count alerts by severity
     * 
     * @param severity The severity to count
     * @return Count of alerts with the specified severity
     */
    long countBySeverity(String severity);
    
    /**
     * Count alerts by status and severity
     * 
     * @param status The status to count
     * @param severity The severity to count
     * @return Count of alerts with the specified status and severity
     */
    long countByStatusAndSeverity(String status, String severity);
    
    /**
     * Find alerts by title or description containing the search term
     * 
     * @param searchTerm The search term
     * @return List of alerts with title or description containing the search term
     */
    @Query("SELECT a FROM IncidentAlert a WHERE " +
           "LOWER(a.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(a.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<IncidentAlert> searchByTitleOrDescription(@Param("searchTerm") String searchTerm);
    
    /**
     * Find alerts reported today
     * 
     * @param startOfDay Start of the day
     * @param endOfDay End of the day
     * @return List of alerts reported today
     */
    List<IncidentAlert> findByReportedAtBetweenOrderByReportedAtDesc(
            LocalDateTime startOfDay, LocalDateTime endOfDay);
    
    /**
     * Find active alerts ordered by severity and reported time
     * 
     * @param status The status to search for (usually "ACTIVE")
     * @return List of active alerts ordered by severity and reported time
     */
    @Query("SELECT a FROM IncidentAlert a WHERE a.status = :status " +
           "ORDER BY CASE a.severity " +
           "  WHEN 'HIGH' THEN 1 " +
           "  WHEN 'MEDIUM' THEN 2 " +
           "  WHEN 'LOW' THEN 3 " +
           "  ELSE 4 END, a.reportedAt DESC")
    List<IncidentAlert> findActiveAlertsByPriority(@Param("status") String status);
}