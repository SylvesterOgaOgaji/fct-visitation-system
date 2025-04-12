package com.fct.visitation.models.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "checkpoints")
public class Checkpoint {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long checkpointId;
    
    @ManyToOne
    @JoinColumn(name = "facility_id", nullable = false)
    private Facility facility;
    
    @Column(nullable = false, length = 100)
    private String locationName;
    
    @Column(length = 255)
    private String description;
    
    // Default constructor
    public Checkpoint() {
    }
    
    // All-args constructor
    public Checkpoint(Long checkpointId, Facility facility, String locationName, String description) {
        this.checkpointId = checkpointId;
        this.facility = facility;
        this.locationName = locationName;
        this.description = description;
    }
    
    // Getters and Setters
    public Long getCheckpointId() {
        return checkpointId;
    }
    
    public void setCheckpointId(Long checkpointId) {
        this.checkpointId = checkpointId;
    }
    
    public Facility getFacility() {
        return facility;
    }
    
    public void setFacility(Facility facility) {
        this.facility = facility;
    }
    
    public String getLocationName() {
        return locationName;
    }
    
    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
}