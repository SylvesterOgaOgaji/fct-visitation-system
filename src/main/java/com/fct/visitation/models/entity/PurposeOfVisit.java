package com.fct.visitation.models.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "purpose_of_visits")
public class PurposeOfVisit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false, length = 20)
    private String code;
    
    @Column(nullable = false, length = 100)
    private String description;
    
    @Column(name = "requires_approval", nullable = false)
    private Boolean requiresApproval = false;
    
    @Column(name = "requires_id_verification", nullable = false)
    private Boolean requiresIdVerification = false;
    
    private Integer maxDurationMinutes;
    
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
    
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;
    
    @Column(insertable = false)
    private LocalDateTime updatedAt;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Facility facility;

    @PrePersist
    void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}