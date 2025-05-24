package com.fct.visitation.models.entity;

import com.fct.visitation.models.enums.VisitorStatus;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Table(name = "visitor")
@Getter @Setter
public class Visitor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") // Ensure this matches foreign key references
    private Long id;
    
    private String firstName;
    private String lastName;
    private String email;

    @Column(name = "phone_number")  
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private VisitorStatus status;

    @ManyToOne
    private Facility facility;

    private LocalDateTime appointmentDatetime;

    @ManyToOne
    private Officer officer;

    @OneToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @Column(name = "qr_code", unique = true)
    private String qrCode;

    public VisitorStatus getStatus() {
        return status;
    }

    public void setStatus(VisitorStatus status) {
        this.status = status;
    }
    
    private LocalDateTime registrationTime;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;

    public void setRegistrationTime(LocalDateTime now) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setCheckInTime(LocalDateTime now) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setCheckOutTime(LocalDateTime now) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setCancellationReason(String reason) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public LocalDateTime getAppointmentDateTime() {
        return appointmentDatetime;
    }
}