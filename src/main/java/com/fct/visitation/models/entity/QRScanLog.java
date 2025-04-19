package com.fct.visitation.models.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

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
    @JoinColumn(name = "checkpoint_id")  // Maps to Checkpoint entity
    private Checkpoint checkpoint;
    
    @Column(name = "scanned_at")  // Renamed from "timestamp"
    private LocalDateTime scannedAt;
    
    // Constructors
    public QRScanLog() {}
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Visitor getVisitor() { return visitor; }
    public void setVisitor(Visitor visitor) { this.visitor = visitor; }
    
    public Checkpoint getCheckpoint() { return checkpoint; }
    public void setCheckpoint(Checkpoint checkpoint) { this.checkpoint = checkpoint; }
    
    public LocalDateTime getScannedAt() { return scannedAt; }
    public void setScannedAt(LocalDateTime scannedAt) { this.scannedAt = scannedAt; }
}