package com.fct.visitation.models.entity;

import com.fct.visitation.models.enums.SpaceType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "parking_spaces")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParkingSpace {
    
    public enum Status {
        AVAILABLE, OCCUPIED, RESERVED, MAINTENANCE
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String spaceNumber;
    
    @Enumerated(EnumType.STRING)
    private Status status;
    
    @Enumerated(EnumType.STRING)
    private SpaceType spaceType;
    
    @ManyToOne
    @JoinColumn(name = "car_details_id")
    private CarDetails carDetails;
    
    @ManyToOne
    @JoinColumn(name = "parking_area_id")
    private ParkingArea parkingArea;
    
    @ManyToOne
    @JoinColumn(name = "visitor_id")
    private Visitor visitor;
    
    private LocalDateTime allocatedAt;
    
    private LocalDateTime releasedAt;
    
    // Explicit setters for status and methods used in controllers
    public void setStatus(Status status) {
        this.status = status;
    }
    
    public void setStatus(String statusStr) {
        try {
            this.status = Status.valueOf(statusStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            this.status = Status.AVAILABLE;
        }
    }
    
    public void setCarDetails(CarDetails carDetails) {
        this.carDetails = carDetails;
    }
    
    public void setVisitor(Visitor visitor) {
        this.visitor = visitor;
    }
    
    public void setAllocatedAt(LocalDateTime allocatedAt) {
        this.allocatedAt = allocatedAt;
    }
    
    public void setReleasedAt(LocalDateTime releasedAt) {
        this.releasedAt = releasedAt;
    }
    
    // Explicit getters
    public Status getStatus() {
        return status;
    }
}