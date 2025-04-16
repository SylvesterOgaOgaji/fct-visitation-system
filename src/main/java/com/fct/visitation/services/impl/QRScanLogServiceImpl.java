package com.fct.visitation.services.impl;

import com.fct.visitation.models.entity.Checkpoint;
import com.fct.visitation.models.entity.QRScanLog;
import com.fct.visitation.models.entity.Visitor;
import com.fct.visitation.repositories.QRScanLogRepository;
import com.fct.visitation.services.interfaces.QRScanLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of the QRScanLogService interface
 */
@Service
public class QRScanLogServiceImpl implements QRScanLogService {

    private final QRScanLogRepository qrScanLogRepository;

    @Autowired
    public QRScanLogServiceImpl(QRScanLogRepository qrScanLogRepository) {
        this.qrScanLogRepository = qrScanLogRepository;
    }

    @Override
    public List<QRScanLog> getAllScanLogs() {
        return qrScanLogRepository.findAll();
    }

    @Override
    public Page<QRScanLog> getAllScanLogs(Pageable pageable) {
        return qrScanLogRepository.findAll(pageable);
    }

    @Override
    public QRScanLog getScanLogById(Long id) {
        return qrScanLogRepository.findById(id).orElse(null);
    }

    @Override
    public List<QRScanLog> getScanLogsByVisitor(Visitor visitor) {
        return qrScanLogRepository.findByVisitor(visitor);
    }

    @Override
    public List<QRScanLog> getScanLogsByCheckpoint(Checkpoint checkpoint) {
        return qrScanLogRepository.findByCheckpoint(checkpoint);
    }

    @Override
    public List<QRScanLog> getScanLogsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return qrScanLogRepository.findByScannedAtBetween(startDate, endDate);
    }

    @Override
    public List<QRScanLog> getScanLogsByScanResult(String scanResult) {
        return qrScanLogRepository.findByScanResult(scanResult);
    }

    @Override
    public List<QRScanLog> getScanLogsByVisitorAndCheckpoint(Visitor visitor, Checkpoint checkpoint) {
        return qrScanLogRepository.findByVisitorAndCheckpoint(visitor, checkpoint);
    }

    @Override
    @Transactional
    public QRScanLog saveQRScanLog(QRScanLog scanLog) {
        // If visitor status before is not set, get it from the visitor
        if (scanLog.getVisitorStatusBefore() == null && scanLog.getVisitor() != null) {
            scanLog.setVisitorStatusBefore(scanLog.getVisitor().getStatus());
        }
        
        return qrScanLogRepository.save(scanLog);
    }

    @Override
    @Transactional
    public void deleteScanLog(Long id) {
        qrScanLogRepository.deleteById(id);
    }

    @Override
    public QRScanLog getLatestScanLogForVisitor(Visitor visitor) {
        return qrScanLogRepository.findFirstByVisitorOrderByScannedAtDesc(visitor);
    }

    @Override
    public List<QRScanLog> getTodayScanLogs() {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);
        return qrScanLogRepository.findByScannedAtBetween(startOfDay, endOfDay);
    }

    @Override
    public Map<Long, Long> getScanCountsByCheckpoint(LocalDateTime startDate, LocalDateTime endDate) {
        List<Object[]> results = qrScanLogRepository.countScansByCheckpoint(startDate, endDate);
        Map<Long, Long> countsByCheckpoint = new HashMap<>();
        
        for (Object[] result : results) {
            Long checkpointId = (Long) result[0];
            Long count = ((Number) result[1]).longValue();
            countsByCheckpoint.put(checkpointId, count);
        }
        
        return countsByCheckpoint;
    }

    @Override
    public Map<Integer, Long> getScanCountsByHourOfDay(LocalDateTime date) {
        List<Object[]> results = qrScanLogRepository.countScansByHourOfDay(date);
        Map<Integer, Long> countsByHour = new HashMap<>();
        
        // Initialize all hours with zero counts
        for (int i = 0; i < 24; i++) {
            countsByHour.put(i, 0L);
        }
        
        // Update with actual counts
        for (Object[] result : results) {
            Integer hour = ((Number) result[0]).intValue();
            Long count = ((Number) result[1]).longValue();
            countsByHour.put(hour, count);
        }
        
        return countsByHour;
    }
}