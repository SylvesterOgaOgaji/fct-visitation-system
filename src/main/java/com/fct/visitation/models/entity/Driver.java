package com.fct.visitation.models.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Must match the repository method reference
    
    private String driverLicense;
    private String driverName;
    private String nin;
    private String phoneNumber;
}