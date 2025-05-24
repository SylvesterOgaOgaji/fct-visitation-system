package com.fct.visitation.models.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "checkpoints")
public class Checkpoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String location;
    private String description;
    
    @Column(name = "is_active")
    private Boolean active;
    
    @Enumerated(EnumType.STRING)
    private CheckpointType checkpointType;
    
    @ManyToOne
    @JoinColumn(name = "facility_id")
    private Facility facility;
}

enum CheckpointType {
    ENTRY, 
    EXIT, 
    BOTH
}