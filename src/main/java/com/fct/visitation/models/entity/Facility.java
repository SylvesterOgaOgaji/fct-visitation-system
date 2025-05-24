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

    @Column(name = "facility_name")
    private String facilityName;

    @Column(unique = true, nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Boolean active = true;

    @Column(nullable = false)
    private String address;

    private String contactNumber;
    private String email;

    // Relationships
    @OneToMany(mappedBy = "facility", cascade = CascadeType.ALL)
    private List<Visitor> visitors;

    @OneToMany(mappedBy = "facility", cascade = CascadeType.ALL)
    private List<Officer> officers;

    // Capacity fields
    private Integer parkingCapacity;
    private Integer visitorCapacity;

    // Geolocation
    private Double latitude;
    private Double longitude;

    @Column(name = "requires_approval")
    private Boolean requiresApproval = false;
}