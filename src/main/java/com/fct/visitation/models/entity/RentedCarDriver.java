package com.fct.visitation.models.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "rented_car_driver")
public class RentedCarDriver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "visitor_id")
    private Visitor visitor;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;
    
    private String nin;
    
    @Column(name = "driver_name")
    private String driverName;
    
    @Column(name = "driver_license")
    private String driverLicense;
    
    @Column(name = "phone_number")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "verification_status")
    private VerificationStatus verificationStatus;

    @Column(name = "verified_by_id")
    private Long verifiedById;

    @Column(name = "verification_timestamp")
    private LocalDateTime verificationTimestamp;

    public enum VerificationStatus {
        PENDING, VERIFIED, REJECTED
    }

    // Constructors
    public RentedCarDriver() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Visitor getVisitor() { return visitor; }
    public void setVisitor(Visitor visitor) { this.visitor = visitor; }

    public Vehicle getVehicle() { return vehicle; }
    public void setVehicle(Vehicle vehicle) { this.vehicle = vehicle; }

    public String getNin() { return nin; }
    public void setNin(String nin) { this.nin = nin; }

    public String getDriverName() { return driverName; }
    public void setDriverName(String driverName) { this.driverName = driverName; }

    public String getDriverLicense() { return driverLicense; }
    public void setDriverLicense(String driverLicense) { this.driverLicense = driverLicense; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public VerificationStatus getVerificationStatus() { return verificationStatus; }
    public void setVerificationStatus(VerificationStatus verificationStatus) { 
        this.verificationStatus = verificationStatus; 
    }

    public Long getVerifiedById() { return verifiedById; }
    public void setVerifiedById(Long verifiedById) { 
        this.verifiedById = verifiedById;
    }

    public LocalDateTime getVerificationTimestamp() { return verificationTimestamp; }
    public void setVerificationTimestamp(LocalDateTime verificationTimestamp) { 
        this.verificationTimestamp = verificationTimestamp;
    }
}