package com.fct.visitation.services.impl;

import com.fct.visitation.models.entity.Checkpoint;
import com.fct.visitation.models.entity.QRScanLog;
import com.fct.visitation.models.entity.Visitor;
import com.fct.visitation.repositories.CheckpointRepository;
import com.fct.visitation.repositories.QRScanLogRepository;
import com.fct.visitation.services.interfaces.QRScanLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class QRScanLogServiceImpl implements QRScanLogService {

    private final QRScanLogRepository qrScanLogRepository;
    private final CheckpointRepository checkpointRepository;

    @Autowired
    public QRScanLogServiceImpl(QRScanLogRepository qrScanLogRepository, CheckpointRepository checkpointRepository) {
        this.qrScanLogRepository = qrScanLogRepository;
        this.checkpointRepository = checkpointRepository;
    }

    @Override
    public QRScanLog recordScan(Visitor visitor, Long checkpointId) {
        Checkpoint checkpoint = checkpointRepository.findById(checkpointId)
                .orElseThrow(() -> new RuntimeException("Checkpoint not found"));
        
        QRScanLog scanLog = new QRScanLog();
        scanLog.setVisitor(visitor);
        scanLog.setCheckpoint(checkpoint);
        scanLog.setScanTime(LocalDateTime.now());
        
        return qrScanLogRepository.save(scanLog);
    }

    @Override
    public List<QRScanLog> findByVisitorId(Long visitorId) {
        return qrScanLogRepository.findByVisitorVisitorId(visitorId);
    }

    @Override
    public List<QRScanLog> findByCheckpointId(Long checkpointId) {
        return qrScanLogRepository.findByCheckpointCheckpointId(checkpointId);
    }

    @Override
    public List<QRScanLog> findByScanTimeBetween(LocalDateTime start, LocalDateTime end) {
        return qrScanLogRepository.findByScanTimeBetween(start, end);
    }
}
