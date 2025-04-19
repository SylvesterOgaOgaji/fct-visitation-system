package com.fct.visitation.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "parking_areas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParkingArea {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    private String location;
    
    private Integer capacity;
    
    @ManyToOne
    @JoinColumn(name = "facility_id")
    private Facility facility;
    
    private boolean active;
}