package com.fct.visitation.models.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "car_details")
public class CarDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long carId;
    
    @ManyToOne
    @JoinColumn(name = "visitor_id", nullable = false)
    private Visitor visitor;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CarType carType;
    
    @Column(nullable = false, length = 20)
    private String registrationNumber;
    
    @Column(nullable = false, length = 50)
    private String model;
    
    @Column(nullable = false, length = 30)
    private String color;
    
    @ManyToOne
    @JoinColumn(name = "driver_id")
    private RentedCarDriver driver;
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    
    public enum CarType {
        OWN, RENTED, NONE
    }
    
    // Constructors
    public CarDetails() {
    }
    
    public CarDetails(Long carId, Visitor visitor, CarType carType, String registrationNumber, String model, String color, RentedCarDriver driver, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.carId = carId;
        this.visitor = visitor;
        this.carType = carType;
        this.registrationNumber = registrationNumber;
        this.model = model;
        this.color = color;
        this.driver = driver;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    // Getters and Setters
    public Long getCarId() {
        return carId;
    }
    
    public void setCarId(Long carId) {
        this.carId = carId;
    }
    
    public Visitor getVisitor() {
        return visitor;
    }
    
    public void setVisitor(Visitor visitor) {
        this.visitor = visitor;
    }
    
    public CarType getCarType() {
        return carType;
    }
    
    public void setCarType(CarType carType) {
        this.carType = carType;
    }
    
    public String getRegistrationNumber() {
        return registrationNumber;
    }
    
    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }
    
    public String getModel() {
        return model;
    }
    
    public void setModel(String model) {
        this.model = model;
    }
    
    public String getColor() {
        return color;
    }
    
    public void setColor(String color) {
        this.color = color;
    }
    
    public RentedCarDriver getDriver() {
        return driver;
    }
    
    public void setDriver(RentedCarDriver driver) {
        this.driver = driver;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}