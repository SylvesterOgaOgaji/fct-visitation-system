package com.fct.visitation.models.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "facilities")
public class Facility {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    @Column(name = "facility_name")
    private String facilityName;
    
    @Column(unique = true)
    private String code;
    
    private String address;
    
    private String description;
    
    @Column(name = "contact_person")
    private String contactPerson;
    
    @Column(name = "contact_phone")
    private String contactPhone;
    
    @Column(name = "contact_number")
    private String contactNumber;
    
    @Column(name = "contact_email")
    private String contactEmail;
    
    private String email;
    
    private boolean active;
    
    @Column(name = "requires_approval")
    private boolean requiresApproval;
    
    private BigDecimal latitude;
    
    private BigDecimal longitude;
    
    @Column(name = "parking_capacity")
    private Integer parkingCapacity;
    
    @Column(name = "visitor_capacity")
    private Integer visitorCapacity;
    
    // Constructors
    public Facility() {}
    
    // Update constructor to include all fields if needed
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getFacilityName() {
        return facilityName;
    }
    
    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
        this.name = facilityName; // Keep both synchronized
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getContactPerson() {
        return contactPerson;
    }
    
    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }
    
    public String getContactPhone() {
        return contactPhone;
    }
    
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }
    
    public String getContactNumber() {
        return contactNumber;
    }
    
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
        this.contactPhone = contactNumber; // Keep both synchronized
    }
    
    public String getContactEmail() {
        return contactEmail;
    }
    
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
        this.contactEmail = email; // Keep both synchronized
    }
    
    public boolean getActive() {
        return active;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
    
    public boolean getRequiresApproval() {
        return requiresApproval;
    }
    
    public void setRequiresApproval(boolean requiresApproval) {
        this.requiresApproval = requiresApproval;
    }
    
    public BigDecimal getLatitude() {
        return latitude;
    }
    
    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }
    
    public BigDecimal getLongitude() {
        return longitude;
    }
    
    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }
    
    public Integer getParkingCapacity() {
        return parkingCapacity;
    }
    
    public void setParkingCapacity(Integer parkingCapacity) {
        this.parkingCapacity = parkingCapacity;
    }
    
    public Integer getVisitorCapacity() {
        return visitorCapacity;
    }
    
    public void setVisitorCapacity(Integer visitorCapacity) {
        this.visitorCapacity = visitorCapacity;
    }
    
    // For backward compatibility with existing code
    public Long getFacilityId() {
        return id;
    }
    
    // Add this method for test compatibility
    public void setFacilityId(long id) {
        this.id = id;
    }
}