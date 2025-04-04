package com.fct.visitation.repositories;

import com.fct.visitation.models.entity.QRScanLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface QRScanLogRepository extends JpaRepository<QRScanLog, Long> {
    List<QRScanLog> findByVisitorVisitorId(Long visitorId);
    List<QRScanLog> findByCheckpointCheckpointId(Long checkpointId);
    List<QRScanLog> findByScanTimeBetween(LocalDateTime start, LocalDateTime end);
}
