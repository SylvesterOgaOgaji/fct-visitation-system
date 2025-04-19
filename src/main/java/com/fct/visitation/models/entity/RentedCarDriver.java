package com.fct.visitation.models.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "rented_car_driver")
public class RentedCarDriver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long carId;
    private String driverName;
    private String driverLicense;
    private String phoneNumber;
    
    @Enumerated(EnumType.STRING)
    private VerificationStatus verificationStatus;
    
    // Define the VerificationStatus enum
    public enum VerificationStatus {
        PENDING, VERIFIED, REJECTED
    }
    
    public RentedCarDriver() {
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getCarId() {
        return carId;
    }
    
    public void setCarId(Long carId) {
        this.carId = carId;
    }
    
    public String getDriverName() {
        return driverName;
    }
    
    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }
    
    public String getDriverLicense() {
        return driverLicense;
    }
    
    public void setDriverLicense(String driverLicense) {
        this.driverLicense = driverLicense;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public VerificationStatus getVerificationStatus() {
        return verificationStatus;
    }
    
    public void setVerificationStatus(VerificationStatus verificationStatus) {
        this.verificationStatus = verificationStatus;
    }
}