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
    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    public enum CarType { 
        SEDAN, 
        SUV, 
        TRUCK 
    }
    
    @Enumerated(EnumType.STRING)
    @Column(name = "car_type")
    private CarType carType;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "visitor_id")
    private Visitor visitor;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

    @Column(name = "registration_number", unique = true)
    private String registrationNumber;
}