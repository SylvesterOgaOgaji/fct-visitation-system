package com.fct.visitation.models.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "visitors")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Visitor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long visitorId;
    
    @Column(nullable = false, length = 50)
    private String firstName;
    
    @Column(nullable = false, length = 50)
    private String lastName;
    
    @Column(nullable = false, unique = true, length = 100)
    private String email;
    
    @Column(nullable = false, length = 15)
    private String phoneNumber;
    
    @Column(nullable = false, unique = true, length = 20)
    private String nin;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CarType carType;
    
    @ManyToOne
    @JoinColumn(name = "facility_id", nullable = false)
    private Facility facility;
    
    @ManyToOne
    @JoinColumn(name = "officer_id", nullable = false)
    private Officer officer;
    
    @ManyToOne
    @JoinColumn(name = "purpose_id", nullable = false)
    private PurposeOfVisit purposeOfVisit;
    
    @Column(nullable = false)
    private LocalDateTime appointmentDateTime;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VisitorStatus status = VisitorStatus.PENDING;
    
    @Column(unique = true)
    private String qrCode;
    
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    
    // Explicit getters and setters to ensure compatibility
    public Long getVisitorId() { return this.visitorId; }
    public void setVisitorId(Long visitorId) { this.visitorId = visitorId; }
    
    public String getFirstName() { return this.firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getLastName() { return this.lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    public String getEmail() { return this.email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPhoneNumber() { return this.phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    
    public String getNin() { return this.nin; }
    public void setNin(String nin) { this.nin = nin; }
    
    public CarType getCarType() { return this.carType; }
    public void setCarType(CarType carType) { this.carType = carType; }
    
    public Facility getFacility() { return this.facility; }
    public void setFacility(Facility facility) { this.facility = facility; }
    
    public Officer getOfficer() { return this.officer; }
    public void setOfficer(Officer officer) { this.officer = officer; }
    
    public PurposeOfVisit getPurposeOfVisit() { return this.purposeOfVisit; }
    public void setPurposeOfVisit(PurposeOfVisit purposeOfVisit) { this.purposeOfVisit = purposeOfVisit; }
    
    public LocalDateTime getAppointmentDateTime() { return this.appointmentDateTime; }
    public void setAppointmentDateTime(LocalDateTime appointmentDateTime) { this.appointmentDateTime = appointmentDateTime; }
    
    public VisitorStatus getStatus() { return this.status; }
    public void setStatus(VisitorStatus status) { this.status = status; }
    
    public String getQrCode() { return this.qrCode; }
    public void setQrCode(String qrCode) { this.qrCode = qrCode; }
    
    // Enums
    public enum CarType {
        OWN, RENTED, NONE
    }
    
    public enum VisitorStatus {
        PENDING, CHECKED_IN, IN_MEETING, COMPLETED
    }
}
