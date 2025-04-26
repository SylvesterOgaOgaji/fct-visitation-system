package com.fct.visitation.models.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@Table(name = "car_details")
public class CarDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    public enum CarType { 
        SEDAN, 
        SUV, 
        TRUCK 
    }
    
    @Enumerated(EnumType.STRING)
    @Column(name = "car_type", length = 20)
    private CarType carType;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visitor_id")
    private Visitor visitor;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id")
    private Driver driver;

    @Column(name = "registration_number", unique = true, length = 20)
    private String registrationNumber;

    // Add other necessary fields
    @Column(name = "manufacturer", length = 50)
    private String manufacturer;
    
    @Column(name = "production_year")
    private Integer productionYear;
}