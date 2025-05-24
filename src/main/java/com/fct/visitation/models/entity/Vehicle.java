package com.fct.visitation.models.entity;

import com.fct.visitation.models.enums.VehicleType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vehicles")
public class Vehicle {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "registration_number", unique = true, nullable = false)
    private String registrationNumber;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private String color;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_type")
    private VehicleType vehicleType;

    @OneToOne(mappedBy = "vehicle")
    private Visitor visitor;

    @Column(name = "driver_name")
    private String driverName;

    @Column(name = "driver_license")
    private String driverLicense;

    public Long getId() {
        return id;
    }

    // Add the getRegistrationNumber method
    public String getRegistrationNumber() {
        return registrationNumber;
    }
}