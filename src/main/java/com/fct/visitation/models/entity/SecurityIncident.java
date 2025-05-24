package com.fct.visitation.models.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class SecurityIncident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String eventType;
    private String description;
    private String location;
    private LocalDateTime incidentTimestamp;

    @Enumerated(EnumType.STRING)
    private IncidentStatus status;

    private LocalDateTime resolutionDate;
    private boolean resolved;

    public enum IncidentStatus { REPORTED, IN_PROGRESS, RESOLVED }

    public void setResolutionDate(LocalDateTime resolutionDate) {
        this.resolutionDate = resolutionDate;
    }

    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }
}