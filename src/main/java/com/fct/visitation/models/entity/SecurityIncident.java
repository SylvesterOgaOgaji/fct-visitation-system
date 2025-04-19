package com.fct.visitation.models.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "security_incidents")
public class SecurityIncident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String eventType;

    @Column(nullable = false)
    private String description;

    @Column
    private String location;

    @Column(nullable = false)
    private LocalDateTime incidentTimestamp;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IncidentStatus status;

    public String getTitle() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getSeverity() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object getTimestamp() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public enum IncidentStatus {
        REPORTED,
        IN_PROGRESS,
        RESOLVED
    }

    // Constructors
    public SecurityIncident() {
        this.incidentTimestamp = LocalDateTime.now();
        this.status = IncidentStatus.REPORTED;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getIncidentTimestamp() {
        return incidentTimestamp;
    }

    public void setIncidentTimestamp(LocalDateTime incidentTimestamp) {
        this.incidentTimestamp = incidentTimestamp;
    }

    public IncidentStatus getStatus() {
        return status;
    }

    public void setStatus(IncidentStatus status) {
        this.status = status;
    }
}