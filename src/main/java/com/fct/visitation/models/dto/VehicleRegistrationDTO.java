package com.fct.visitation.models.dto;

import com.fct.visitation.models.entity.CarDetails;
import com.fct.visitation.models.enums.CarType;

public class VehicleRegistrationDTO {
    private CarType carType;
    private String registrationNumber;
    private String model;
    private String color;
    
    // Only used if carType is RENTED
    private RentedCarDriverDTO rentedCarDriver;
    
    // Constructors
    public VehicleRegistrationDTO() {
    }
    
    public VehicleRegistrationDTO(CarType carType, String registrationNumber, String model, String color, RentedCarDriverDTO rentedCarDriver) {
        this.carType = carType;
        this.registrationNumber = registrationNumber;
        this.model = model;
        this.color = color;
        this.rentedCarDriver = rentedCarDriver;
    }
    
    // Getters and Setters
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
    
    public RentedCarDriverDTO getRentedCarDriver() {
        return rentedCarDriver;
    }
    
    public void setRentedCarDriver(RentedCarDriverDTO rentedCarDriver) {
        this.rentedCarDriver = rentedCarDriver;
    }
}