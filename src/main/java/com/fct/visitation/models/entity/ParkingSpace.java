package com.fct.visitation.models.entity;

import com.fct.visitation.models.enums.SpaceType;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
public class ParkingSpace {
    public enum Status { AVAILABLE, OCCUPIED, RESERVED, MAINTENANCE }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Status status = Status.AVAILABLE;

    @Column(name = "space_type")
    @Enumerated(EnumType.STRING)
    private SpaceType spaceType;

    
    @Column(unique = true)
    private String spaceNumber;
    private String locationCode;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private CarDetails carDetails;

    @ManyToOne(fetch = FetchType.LAZY)
    private ParkingArea parkingArea;

    @ManyToOne(fetch = FetchType.LAZY)
    private Visitor visitor;
    
    private LocalDateTime allocatedAt;
    private LocalDateTime releasedAt;

    public void updateStatus(String status) {
        try {
            this.status = Status.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            this.status = Status.AVAILABLE;
        }
    }
}