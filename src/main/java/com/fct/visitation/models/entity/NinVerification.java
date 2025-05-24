package com.fct.visitation.models.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Getter 
@Setter
@Table(name = "nin_verifications")
public class NinVerification {
    private Long contextId;
    public enum Gender { MALE, FEMALE, OTHER }

    @Entity
@Getter @Setter
public class IncidentAlert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public enum Status {
        REPORTED, IN_PROGRESS, RESOLVED  // Add these enum values
    }
    
    @Enumerated(EnumType.STRING)
    private Status status;
    
    private LocalDateTime reportedAt;  // Add this field
    // other fields...
}

@Entity
@Getter @Setter
public class Visitor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String phoneNumber;  // Add this field
    // other fields...
}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nin;

    @Enumerated(EnumType.STRING)
    private Gender gender;
    
    private Boolean verified = false;
    private LocalDateTime verificationTimestamp;
}