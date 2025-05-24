package com.fct.visitation.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "checkpoint_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckpointLog {
    
    @Id
    @GeneratedValue(generator = "UUID")
    private String id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visitor_id")
    private Visitor visitor;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "checkpoint_id")
    private Checkpoint checkpoint;
    
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    
    @Enumerated(EnumType.STRING)
    private LogStatus status;
    
    private String notes;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "processed_by")
    private SecurityPersonnel processedBy;

    public enum LogStatus {
        ENTERED, EXITED
    }
}