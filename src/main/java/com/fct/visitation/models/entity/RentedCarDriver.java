package com.fct.visitation.models.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@Table(name = "rented_car_drivers")
public class RentedCarDriver {
    public enum VerificationStatus { PENDING, VERIFIED, REJECTED }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Visitor visitor;

    @ManyToOne(fetch = FetchType.LAZY)
    private Vehicle vehicle;
    
    @Column(name = "national_id")
    private String nationalId;
    
    private String driverName;
    private String driverLicense;
    private String phoneNumber;
    
    @Enumerated(EnumType.STRING)
    private VerificationStatus verificationStatus;
    
    @Column(name = "verified_by")
    private Long verifiedById;
    
    private LocalDateTime verificationTimestamp;

    @Column(name = "nin", unique = true)
    private String nin;
}