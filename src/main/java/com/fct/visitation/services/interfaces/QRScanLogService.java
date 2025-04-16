package com.fct.visitation.services.interfaces;

import com.fct.visitation.models.entity.Checkpoint;
import com.fct.visitation.models.entity.QRScanLog;
import com.fct.visitation.models.entity.Visitor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Service interface for QR code scan log operations
 */
public interface QRScanLogService {
    
    /**
     * Get all scan logs
     * 
     * @return List of all scan logs
     */
    List<QRScanLog> getAllScanLogs();
    
    /**
     * Get scan logs with pagination
     * 
     * @param pageable Pagination information
     * @return Page of scan logs
     */
    Page<QRScanLog> getAllScanLogs(Pageable pageable);
    
    /**
     * Get scan log by ID
     * 
     * @param id Scan log ID
     * @return Scan log if found, or null
     */
    QRScanLog getScanLogById(Long id);
    
    /**
     * Get scan logs for a specific visitor
     * 
     * @param visitor The visitor to search for
     * @return List of scan logs for the specified visitor
     */
    List<QRScanLog> getScanLogsByVisitor(Visitor visitor);
    
    /**
     * Get scan logs for a specific checkpoint
     * 
     * @param checkpoint The checkpoint to search for
     * @return List of scan logs for the specified checkpoint
     */
    List<QRScanLog> getScanLogsByCheckpoint(Checkpoint checkpoint);
    
    /**
     * Get scan logs created within a date range
     * 
     * @param startDate Start of date range
     * @param endDate End of date range
     * @return List of scan logs created in the specified date range
     */
    List<QRScanLog> getScanLogsByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * Get scan logs by scan result
     * 
     * @param scanResult The scan result to search for
     * @return List of scan logs with the specified result
     */
    List<QRScanLog> getScanLogsByScanResult(String scanResult);
    
    /**
     * Get scan logs for a specific visitor at a specific checkpoint
     * 
     * @param visitor The visitor to search for
     * @param checkpoint The checkpoint to search for
     * @return List of scan logs for the specified visitor at the specified checkpoint
     */
    List<QRScanLog> getScanLogsByVisitorAndCheckpoint(Visitor visitor, Checkpoint checkpoint);
    
    /**
     * Save or update a scan log
     * 
     * @param scanLog The scan log to save or update
     * @return The saved scan log
     */
    QRScanLog saveQRScanLog(QRScanLog scanLog);
    
    /**
     * Delete a scan log
     * 
     * @param id Scan log ID
     */
    void deleteScanLog(Long id);
    
    /**
     * Get the most recent scan log for a visitor
     * 
     * @param visitor The visitor to search for
     * @return The most recent scan log for the specified visitor, or null if none found
     */
    QRScanLog getLatestScanLogForVisitor(Visitor visitor);
    
    /**
     * Get scan logs for today
     * 
     * @return List of scan logs created today
     */
    List<QRScanLog> getTodayScanLogs();
    
    /**
     * Get scan counts by checkpoint for a specific date range
     * 
     * @param startDate Start of date range
     * @param endDate End of date range
     * @return Map of checkpoint IDs to scan counts
     */
    Map<Long, Long> getScanCountsByCheckpoint(LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * Get scan counts by hour of day for a specific date
     * 
     * @param date The date to analyze
     * @return Map of hours to scan counts
     */
    Map<Integer, Long> getScanCountsByHourOfDay(LocalDateTime date);
}