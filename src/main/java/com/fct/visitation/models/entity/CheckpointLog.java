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
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @ManyToOne
    @JoinColumn(name = "visitor_id")
    private Visitor visitor;
    
    @ManyToOne
    @JoinColumn(name = "checkpoint_id")
    private Checkpoint checkpoint;
    
    private LocalDateTime entryTime;
    
    private LocalDateTime exitTime;
    
    private String status; // ENTERED, EXITED
    
    private String notes;
    
    @ManyToOne
    @JoinColumn(name = "processed_by")
    private SecurityPersonnel processedBy;
}