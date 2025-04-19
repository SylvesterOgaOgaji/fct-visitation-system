package com.fct.visitation.services.impl;

import com.fct.visitation.models.entity.QRScanLog;
import com.fct.visitation.models.entity.Visitor;
import com.fct.visitation.models.entity.Checkpoint;
import com.fct.visitation.repositories.QRScanLogRepository;
import com.fct.visitation.repositories.CheckpointRepository;
import com.fct.visitation.services.interfaces.QRScanLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class QRScanLogServiceImpl implements QRScanLogService {

    @Autowired
    private QRScanLogRepository qrScanLogRepository;

    @Autowired
    private CheckpointRepository checkpointRepository;

    @Override
    public List<QRScanLog> findAll() {
        return qrScanLogRepository.findAll();
    }

    @Override
    public QRScanLog findById(Long id) {
        return qrScanLogRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public QRScanLog save(QRScanLog qrScanLog) {
        return qrScanLogRepository.save(qrScanLog);
    }

    @Override
    public void deleteById(Long id) {
        qrScanLogRepository.deleteById(id);
    }

    @Override
    public List<QRScanLog> findByVisitorId(Long visitorId) {
        return qrScanLogRepository.findByVisitorId(visitorId);
    }

    @Override
    public List<QRScanLog> findByCheckpointId(Long checkpointId) {
        return qrScanLogRepository.findByCheckpoint_Id(checkpointId);
    }

    @Override
    public QRScanLog findLatestScanByVisitorId(Long visitorId) {
        return qrScanLogRepository.findTopByVisitorIdOrderByScannedAtDesc(visitorId)
                                  .orElse(null);
    }

    @Override
    public Map<Long, Long> getCheckpointStatistics() {
        List<Object[]> rawResults = qrScanLogRepository.countScansByCheckpoint();
        Map<Long, Long> statsMap = new HashMap<>();
        for (Object[] result : rawResults) {
            if (result.length >= 2 && result[0] instanceof Long && result[1] instanceof Long) {
                statsMap.put((Long) result[0], (Long) result[1]);
            }
        }
        return statsMap;
    }

    @Override
    public Map<Integer, Long> getHourlyStatistics() {
        return convertToHourlyMap(qrScanLogRepository.countScansByHour());
    }

    @Override
    @Transactional
    public QRScanLog recordScan(Visitor visitor, Long checkpointId) {
        QRScanLog scanLog = new QRScanLog();
        scanLog.setVisitor(visitor);

        Checkpoint checkpoint = checkpointRepository.findById(checkpointId)
            .orElseThrow(() -> new IllegalArgumentException("Checkpoint not found"));
        scanLog.setCheckpoint(checkpoint);

        scanLog.setScannedAt(LocalDateTime.now());
        return qrScanLogRepository.save(scanLog);
    }

    private Map<Integer, Long> convertToHourlyMap(List<Object[]> results) {
        Map<Integer, Long> statsMap = new HashMap<>();
        for (Object[] result : results) {
            if (result.length >= 2) {
                Integer hour = ((Number) result[0]).intValue();
                Long count = ((Number) result[1]).longValue();
                statsMap.put(hour, count);
            }
        }
        return statsMap;
    }
}