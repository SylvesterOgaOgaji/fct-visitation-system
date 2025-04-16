package com.fct.visitation.models.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity representing a log entry when a visitor's QR code is scanned at a checkpoint
 */
@Entity
@Table(name = "qr_scan_logs")
public class QRScanLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "visitor_id", nullable = false)
    private Visitor visitor;
    
    @ManyToOne
    @JoinColumn(name = "checkpoint_id", nullable = false)
    private Checkpoint checkpoint;
    
    @Column(name = "scanned_at", nullable = false)
    private LocalDateTime scannedAt;
    
    @Column(name = "scanned_by", length = 100)
    private String scannedBy;
    
    @Column(name = "scan_result", length = 20)
    private String scanResult; // SUCCESS, DENIED, EXPIRED
    
    @Column(name = "notes", length = 255)
    private String notes;
    
    @Column(name = "visitor_status_before", length = 20)
    private String visitorStatusBefore;
    
    @Column(name = "visitor_status_after", length = 20)
    private String visitorStatusAfter;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (scannedAt == null) {
            scannedAt = LocalDateTime.now();
        }
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Visitor getVisitor() {
        return visitor;
    }

    public void setVisitor(Visitor visitor) {
        this.visitor = visitor;
    }

    public Checkpoint getCheckpoint() {
        return checkpoint;
    }

    public void setCheckpoint(Checkpoint checkpoint) {
        this.checkpoint = checkpoint;
    }

    public LocalDateTime getScannedAt() {
        return scannedAt;
    }

    public void setScannedAt(LocalDateTime scannedAt) {
        this.scannedAt = scannedAt;
    }

    public String getScannedBy() {
        return scannedBy;
    }

    public void setScannedBy(String scannedBy) {
        this.scannedBy = scannedBy;
    }

    public String getScanResult() {
        return scanResult;
    }

    public void setScanResult(String scanResult) {
        this.scanResult = scanResult;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getVisitorStatusBefore() {
        return visitorStatusBefore;
    }

    public void setVisitorStatusBefore(String visitorStatusBefore) {
        this.visitorStatusBefore = visitorStatusBefore;
    }

    public String getVisitorStatusAfter() {
        return visitorStatusAfter;
    }

    public void setVisitorStatusAfter(String visitorStatusAfter) {
        this.visitorStatusAfter = visitorStatusAfter;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    // toString method
    @Override
    public String toString() {
        return "QRScanLog{" +
                "id=" + id +
                ", visitor=" + (visitor != null ? visitor.getId() : null) +
                ", checkpoint=" + (checkpoint != null ? checkpoint.getName() : null) +
                ", scannedAt=" + scannedAt +
                ", scannedBy='" + scannedBy + '\'' +
                ", scanResult='" + scanResult + '\'' +
                '}';
    }
}