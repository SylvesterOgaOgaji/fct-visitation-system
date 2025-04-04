package com.fct.visitation.services.interfaces;

import com.fct.visitation.models.entity.QRScanLog;
import com.fct.visitation.models.entity.Visitor;

import java.time.LocalDateTime;
import java.util.List;

public interface QRScanLogService {
    QRScanLog recordScan(Visitor visitor, Long checkpointId);
    List<QRScanLog> findByVisitorId(Long visitorId);
    List<QRScanLog> findByCheckpointId(Long checkpointId);
    List<QRScanLog> findByScanTimeBetween(LocalDateTime start, LocalDateTime end);
}
