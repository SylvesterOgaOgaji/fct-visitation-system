package com.fct.visitation.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "officers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Officer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long officerId;
    
    @ManyToOne
    @JoinColumn(name = "facility_id", nullable = false)
    private Facility facility;
    
    @Column(nullable = false, length = 100)
    private String name;
    
    @Column(nullable = false, length = 50)
    private String position;
    
    @Column(nullable = false, length = 50)
    private String department;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DutyStatus dutyStatus = DutyStatus.ON_DUTY;
    
    @Column(length = 100)
    private String email;
    
    @Column(length = 15)
    private String phoneNumber;
    
    public enum DutyStatus {
        ON_DUTY, OFF_DUTY
    }
}
