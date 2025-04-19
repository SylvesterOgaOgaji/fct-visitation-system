package com.fct.visitation.services.interfaces;

import com.fct.visitation.models.entity.QRScanLog;
import com.fct.visitation.models.entity.Visitor;
import java.util.List;
import java.util.Map;

public interface QRScanLogService {
    List<QRScanLog> findAll();
    QRScanLog findById(Long id);
    QRScanLog save(QRScanLog qrScanLog);
    void deleteById(Long id);
    List<QRScanLog> findByVisitorId(Long visitorId);
    List<QRScanLog> findByCheckpointId(Long checkpointId);
    QRScanLog findLatestScanByVisitorId(Long visitorId);
    Map<Long, Long> getCheckpointStatistics();
    Map<Integer, Long> getHourlyStatistics();
    
    // Change void to QRScanLog
    QRScanLog recordScan(Visitor visitor, Long checkpointId);
}