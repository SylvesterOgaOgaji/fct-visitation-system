package com.fct.visitation.models.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@Table(name = "qr_scan_logs")
public class QRScanLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Visitor visitor;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Checkpoint checkpoint;
    
    private LocalDateTime scannedAt;
    
    @Column(length = 100)
    private String scanResult;
}