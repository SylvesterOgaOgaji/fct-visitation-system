package com.fct.visitation.models.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Data; 
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor 
@Entity
@Table(name = "qr_scan_log")
public class QRScanLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "visitor_id")
    private Visitor visitor;
    
    @ManyToOne
    @JoinColumn(name = "checkpoint_id")
    private Checkpoint checkpoint;
    
    @Column(name = "scanned_at")
    private LocalDateTime scannedAt;
    
    // Adding the missing field
    @Column(name = "scan_result", length = 100)
    private String scanResult;
    
    }