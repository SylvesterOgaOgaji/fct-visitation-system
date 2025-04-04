package com.fct.visitation.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    
    // Enums
    public enum CarType {
        OWN, RENTED, NONE
    }
    
    public enum VisitorStatus {
        PENDING, CHECKED_IN, IN_MEETING, COMPLETED
    }
}
