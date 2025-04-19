package com.fct.visitation.repositories;

import com.fct.visitation.models.entity.QRScanLog;
import com.fct.visitation.models.entity.Checkpoint;
import com.fct.visitation.models.entity.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface QRScanLogRepository extends JpaRepository<QRScanLog, Long> {
    List<QRScanLog> findByCheckpoint(Checkpoint checkpoint);
    List<QRScanLog> findByVisitor(Visitor visitor);
    
    Optional<QRScanLog> findFirstByVisitorOrderByScannedAtDesc(Visitor visitor);
    List<QRScanLog> findByScannedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<QRScanLog> findByScanResult(String scanResult);
    Optional<QRScanLog> findByVisitorAndCheckpoint(Visitor visitor, Checkpoint checkpoint);

    // Updated query to use "scannedAt"
    @Query("SELECT c.id, COUNT(q) FROM QRScanLog q JOIN q.checkpoint c GROUP BY c.id")
    List<Object[]> countScansByCheckpoint();

    // Updated query to use "scannedAt"
    @Query("SELECT HOUR(q.scannedAt), COUNT(q) FROM QRScanLog q GROUP BY HOUR(q.scannedAt)")
    List<Object[]> countScansByHour();

    List<QRScanLog> findByVisitorId(Long visitorId);
    List<QRScanLog> findByCheckpointId(Long checkpointId);

    // Corrected method name to use "ScannedAt"
    Optional<QRScanLog> findTopByVisitorIdOrderByScannedAtDesc(Long visitorId);

    @Query("SELECT c.id, COUNT(q) FROM QRScanLog q JOIN q.checkpoint c " +
           "WHERE q.scannedAt BETWEEN :startDate AND :endDate GROUP BY c.id")
    List<Object[]> countScansByCheckpointBetweenDates(
        @Param("startDate") LocalDateTime startDate, 
        @Param("endDate") LocalDateTime endDate
    );

    public Object findTopByVisitorIdOrderByTimestampDesc(Long visitorId);
}