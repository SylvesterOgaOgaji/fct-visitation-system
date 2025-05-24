package com.fct.visitation.models.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "officers")
public class Officer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") // changed from "officer_id" to "id"
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "title")
    private String title;

    @Column(name = "staff_id", unique = true)
    private String staffId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_id")
    private Facility facility;

    @Column(name = "office_number")
    private String officeNumber;

    @Column(name = "is_active")
    private Boolean active = true;

    @Column(name = "availability_status")
    private String availabilityStatus;

    @Column(name = "max_visitors_per_day")
    private Integer maxVisitorsPerDay;

    @Column(name = "requires_approval")
    private Boolean requiresApproval = false;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "last_login")
    private Timestamp lastLogin;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "department")
    private String department;

    @Transient
    public String getFullName() {
        return firstName + " " + lastName;
    }
}