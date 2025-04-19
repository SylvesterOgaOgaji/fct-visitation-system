package com.fct.visitation.models.dto;

public class RentedCarDriverDTO {
    private String name;
    private String nin;
    private String licenseNumber;
    private String contactNumber;
    
    // Constructors
    public RentedCarDriverDTO() {
    }
    
    public RentedCarDriverDTO(String name, String nin, String licenseNumber, String contactNumber) {
        this.name = name;
        this.nin = nin;
        this.licenseNumber = licenseNumber;
        this.contactNumber = contactNumber;
    }
    
    // Getters and Setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getNin() {
        return nin;
    }
    
    public void setNin(String nin) {
        this.nin = nin;
    }
    
    public String getLicenseNumber() {
        return licenseNumber;
    }
    
    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }
    
    public String getContactNumber() {
        return contactNumber;
    }
    
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}