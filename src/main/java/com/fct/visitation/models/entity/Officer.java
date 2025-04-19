package com.fct.visitation.models.entity;

import com.fct.visitation.models.enums.DutyStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "officers")
public class Officer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String firstName;
    
    private String lastName;
    
    @ManyToOne
    @JoinColumn(name = "facility_id")
    private Facility facility;
    
    private String department;
    
    private String email;
    
    private String phoneNumber;
    
    @Enumerated(EnumType.STRING)
    private DutyStatus dutyStatus;
    
    private boolean active;
    
    // Constructors
    public Officer() {}
    
    public Officer(String firstName, String lastName, Facility facility, 
                   String department, String email, String phoneNumber, 
                   DutyStatus dutyStatus, boolean active) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.facility = facility;
        this.department = department;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dutyStatus = dutyStatus;
        this.active = active;
    }

    // Add these methods
public void setOfficerId(long id) {
    this.id = id;
}

public Long getOfficerId() {
    return this.id;
}

public void setName(String name) {
    this.firstName = name.split(" ")[0];
    if (name.split(" ").length > 1) {
        this.lastName = name.substring(name.indexOf(" ") + 1);
    }
}

public String getName() {
    return this.getFullName();
}

public void setPosition(String position) {
    this.department = position;
}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    public Facility getFacility() { return facility; }
    public void setFacility(Facility facility) { this.facility = facility; }
    
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    
    public DutyStatus getDutyStatus() { return dutyStatus; }
    public void setDutyStatus(DutyStatus dutyStatus) { this.dutyStatus = dutyStatus; }
    
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    
    // Convenience method
    public String getFullName() {
        return firstName + " " + lastName;
    }
}