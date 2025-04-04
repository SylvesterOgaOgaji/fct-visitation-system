package com.fct.visitation.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "qr_scan_log")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QRScanLog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scanId;
    
    @ManyToOne
    @JoinColumn(name = "visitor_id", nullable = false)
    private Visitor visitor;
    
    @ManyToOne
    @JoinColumn(name = "checkpoint_id", nullable = false)
    private Checkpoint checkpoint;
    
    @Column(nullable = false)
    private LocalDateTime scanTime;
}
