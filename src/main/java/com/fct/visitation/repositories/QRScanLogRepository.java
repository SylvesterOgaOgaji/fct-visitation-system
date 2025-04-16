package com.fct.visitation.repositories;

import com.fct.visitation.models.entity.Checkpoint;
import com.fct.visitation.models.entity.QRScanLog;
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
 * Repository interface for QRScanLog entity operations
 */
@Repository
public interface QRScanLogRepository extends JpaRepository<QRScanLog, Long> {
    
    /**
     * Find scan logs for a specific visitor
     * 
     * @param visitor The visitor to search for
     * @return List of scan logs for the specified visitor
     */
    List<QRScanLog> findByVisitor(Visitor visitor);
    
    /**
     * Find scan logs for a specific visitor with pagination
     * 
     * @param visitor The visitor to search for
     * @param pageable Pagination information
     * @return Page of scan logs for the specified visitor
     */
    Page<QRScanLog> findByVisitor(Visitor visitor, Pageable pageable);
    
    /**
     * Find scan logs for a specific checkpoint
     * 
     * @param checkpoint The checkpoint to search for
     * @return List of scan logs for the specified checkpoint
     */
    List<QRScanLog> findByCheckpoint(Checkpoint checkpoint);
    
    /**
     * Find scan logs by scan result
     * 
     * @param scanResult The scan result to search for
     * @return List of scan logs with the specified result
     */
    List<QRScanLog> findByScanResult(String scanResult);
    
    /**
     * Find scan logs by the security personnel who performed the scan
     * 
     * @param scannedBy The name of the security personnel
     * @return List of scan logs performed by the specified personnel
     */
    List<QRScanLog> findByScannedBy(String scannedBy);
    
    /**
     * Find scan logs created within a date range
     * 
     * @param startDate Start of date range
     * @param endDate End of date range
     * @return List of scan logs created in the specified date range
     */
    List<QRScanLog> findByScannedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * Find scan logs for a specific visitor at a specific checkpoint
     * 
     * @param visitor The visitor to search for
     * @param checkpoint The checkpoint to search for
     * @return List of scan logs for the specified visitor at the specified checkpoint
     */
    List<QRScanLog> findByVisitorAndCheckpoint(Visitor visitor, Checkpoint checkpoint);
    
    /**
     * Find scan logs for a specific visitor within a date range
     * 
     * @param visitor The visitor to search for
     * @param startDate Start of date range
     * @param endDate End of date range
     * @return List of scan logs for the specified visitor in the date range
     */
    List<QRScanLog> findByVisitorAndScannedAtBetween(
            Visitor visitor, LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * Find scan logs for a specific checkpoint within a date range
     * 
     * @param checkpoint The checkpoint to search for
     * @param startDate Start of date range
     * @param endDate End of date range
     * @return List of scan logs for the specified checkpoint in the date range
     */
    List<QRScanLog> findByCheckpointAndScannedAtBetween(
            Checkpoint checkpoint, LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * Count scan logs by result
     * 
     * @param scanResult The scan result to count
     * @return Count of scan logs with the specified result
     */
    long countByScanResult(String scanResult);
    
    /**
     * Find the most recent scan log for a visitor
     * 
     * @param visitor The visitor to search for
     * @return The most recent scan log for the specified visitor, or null if none found
     */
    QRScanLog findFirstByVisitorOrderByScannedAtDesc(Visitor visitor);
    
    /**
     * Get scan counts by checkpoint for a specific date range
     * 
     * @param startDate Start of date range
     * @param endDate End of date range
     * @return List of objects containing checkpoint ID and scan count
     */
    @Query("SELECT l.checkpoint.id as checkpointId, COUNT(l) as scanCount " +
           "FROM QRScanLog l " +
           "WHERE l.scannedAt BETWEEN :startDate AND :endDate " +
           "GROUP BY l.checkpoint.id")
    List<Object[]> countScansByCheckpoint(
            @Param("startDate") LocalDateTime startDate, 
            @Param("endDate") LocalDateTime endDate);
    
    /**
     * Get scan counts by hour of day for a specific date
     * 
     * @param date The date to analyze
     * @return List of objects containing hour and scan count
     */
    @Query("SELECT FUNCTION('HOUR', l.scannedAt) as hour, COUNT(l) as scanCount " +
           "FROM QRScanLog l " +
           "WHERE FUNCTION('DATE', l.scannedAt) = FUNCTION('DATE', :date) " +
           "GROUP BY FUNCTION('HOUR', l.scannedAt)")
    List<Object[]> countScansByHourOfDay(@Param("date") LocalDateTime date);
    
    /**
     * Find successful scans (i.e., where visitor status changed)
     * 
     * @return List of scan logs where the visitor status changed
     */
    @Query("SELECT l FROM QRScanLog l WHERE l.visitorStatusBefore != l.visitorStatusAfter")
    List<QRScanLog> findSuccessfulStatusChanges();
}