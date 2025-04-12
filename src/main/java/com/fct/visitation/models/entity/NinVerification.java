package com.fct.visitation.models.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "nin_verifications")
public class NinVerification {
    @Id
    @Column(name = "nin", length = 20)
    private String nin;
    
    @Column(nullable = false, length = 100)
    private String firstName;
    
    @Column(nullable = false, length = 100)
    private String lastName;
    
    @Column(nullable = false)
    private LocalDate dateOfBirth;
    
    @Column(length = 10)
    private String gender;
    
    @Column(length = 200)
    private String address;
    
    @Column(length = 100)
    private String phoneNumber;
    
    @Column(nullable = false)
    private boolean verified = true;
    
    // Default constructor
    public NinVerification() {
    }
    
    // All-args constructor
    public NinVerification(String nin, String firstName, String lastName, LocalDate dateOfBirth, 
                          String gender, String address, String phoneNumber, boolean verified) {
        this.nin = nin;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.verified = verified;
    }
    
    // Getters and Setters
    public String getNin() {
        return nin;
    }
    
    public void setNin(String nin) {
        this.nin = nin;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    public String getGender() {
        return gender;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public boolean isVerified() {
        return verified;
    }
    
    public void setVerified(boolean verified) {
        this.verified = verified;
    }
}