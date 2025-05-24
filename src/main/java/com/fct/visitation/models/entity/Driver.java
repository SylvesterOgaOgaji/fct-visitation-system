package com.fct.visitation.models.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "drivers")
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "license_number", unique = true)
    private String licenseNumber;
    
    private String fullName;
    
    @Column(name = "national_id", unique = true)
    private String nationalId;
    
    private String phoneNumber;
    
    @Column(name = "is_approved", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean approved;
}