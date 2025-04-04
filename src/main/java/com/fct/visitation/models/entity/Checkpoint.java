package com.fct.visitation.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "checkpoints")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Checkpoint {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long checkpointId;
    
    @Column(nullable = false, length = 100)
    private String locationName;
    
    @Column(length = 255)
    private String description;
}
