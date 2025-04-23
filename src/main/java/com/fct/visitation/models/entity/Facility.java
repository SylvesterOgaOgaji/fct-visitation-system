package com.fct.visitation.models.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "facilities")
public class Facility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", unique = true, nullable = false)
    private String code;

    @Column(name = "facility_name", nullable = false)
    private String facilityName;

    @Column(nullable = false, columnDefinition = "BIT DEFAULT TRUE")
    private boolean active = true;

    @Column(nullable = false)
    private String address;

    @Column(name = "contact_number")
    private String contactNumber;

    private String email;

    // Relationships
    @OneToMany(mappedBy = "facility")
    private List<Visitor> visitors;

    @OneToMany(mappedBy = "facility")
    private List<Officer> officers;

    // Additional useful fields
    @Column(name = "parking_capacity")
    private Integer parkingCapacity;

    @Column(name = "visitor_capacity")
    private Integer visitorCapacity;

    private Double latitude;
    private Double longitude;

    @Column(name = "requires_approval", columnDefinition = "BIT DEFAULT FALSE")
    private boolean requiresApproval;
}