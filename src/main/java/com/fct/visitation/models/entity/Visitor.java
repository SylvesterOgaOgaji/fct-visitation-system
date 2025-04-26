package com.fct.visitation.models.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Entity
@Table(name = "visitors")
public class Visitor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Basic Information
    @Column(name = "first_name")
    private String firstName;
    
    @Column(name = "last_name")
    private String lastName;
    
    @Column(unique = true)
    private String email;
    
    @Column(name = "phone_number")
    private String phoneNumber;

    // Visitor Tracking
    @Column(name = "qr_code")
    private String qrCode;
    
    @Enumerated(EnumType.STRING)
    private VisitorStatus status;
    
    // Timestamps
    @Column(name = "registration_time")
    private LocalDateTime registrationTime;
    
    @Column(name = "check_in_time")
    private LocalDateTime checkInTime;
    
    @Column(name = "check_out_time")
    private LocalDateTime checkOutTime;

    // Cancellation
    @Column(name = "cancellation_reason")
    private String cancellationReason;

    // Relationships
    @OneToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "facility_id")
    private Facility facility;

    @ManyToOne
    @JoinColumn(name = "officer_id")
    private Officer officer;

    // Appointment
    @Column(name = "appointment_datetime")
    private LocalDateTime appointmentDatetime;
}