package com.fct.visitation.models.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "checkpoints")
public class Checkpoint {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    private String location;
    
    private String description;
    
    private boolean isActive;
    
    private String checkpointType; // ENTRY, EXIT, BOTH
    
    @ManyToOne
    @JoinColumn(name = "facility_id")
    private Facility facility;
    
    // Constructors
    public Checkpoint() {
    }
    
    public Checkpoint(Long id, String name, String location, String description, boolean isActive, String checkpointType, Facility facility) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.description = description;
        this.isActive = isActive;
        this.checkpointType = checkpointType;
        this.facility = facility;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public boolean isIsActive() {
        return isActive;
    }
    
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
    
    public String getCheckpointType() {
        return checkpointType;
    }
    
    public void setCheckpointType(String checkpointType) {
        this.checkpointType = checkpointType;
    }
    
    public Facility getFacility() {
        return facility;
    }
    
    public void setFacility(Facility facility) {
        this.facility = facility;
    }
    
    // Alternative getter for locationName to maintain compatibility with existing code
    public String getLocationName() {
        return name;
    }
    
    // Alternative setter for locationName to maintain compatibility with existing code
    public void setLocationName(String locationName) {
        this.name = locationName;
    }
}