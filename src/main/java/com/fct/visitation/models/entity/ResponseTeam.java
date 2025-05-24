package com.fct.visitation.models.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "response_teams")
public class ResponseTeam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") // changed from "team_id" to "id"
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "description")
    private String description;

    @Column(name = "team_leader")
    private String teamLeader;

    @Column(name = "contact_number", nullable = false)
    private String contactNumber;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "location")
    private String location;

    @Column(name = "availability_status")
    private String availabilityStatus;

    @Column(name = "is_active")
    private Boolean isActive = true;

    public String getContactEmail() {
        return email;
    }
}
