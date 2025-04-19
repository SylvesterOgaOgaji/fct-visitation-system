package com.fct.visitation.models.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "incident_alert")
public class IncidentAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;
    private String description;
    private LocalDateTime timestamp;
    private String location;
    private String severity;
    
    // Add missing fields
    private LocalDateTime reportedAt;
    private String reportedBy;
    
    @ManyToOne
    @JoinColumn(name = "visitor_id")
    private Visitor visitor;
    
    private String responseTeam;
    
    @Enumerated(EnumType.STRING)
    private Status status;
    
    // Define the Status enum
    public enum Status {
        REPORTED, PENDING, IN_PROGRESS, RESOLVED, CLOSED
    }
    
    public IncidentAlert() {
    }
    
    // Existing getters and setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public String getSeverity() {
        return severity;
    }
    
    public void setSeverity(String severity) {
        this.severity = severity;
    }
    
    // Add missing getters and setters
    public LocalDateTime getReportedAt() {
        return reportedAt;
    }
    
    public void setReportedAt(LocalDateTime reportedAt) {
        this.reportedAt = reportedAt;
    }
    
    public String getReportedBy() {
        return reportedBy;
    }
    
    public void setReportedBy(String reportedBy) {
        this.reportedBy = reportedBy;
    }
    
    public Visitor getVisitor() {
        return visitor;
    }
    
    public void setVisitor(Visitor visitor) {
        this.visitor = visitor;
    }
    
    public String getResponseTeam() {
        return responseTeam;
    }
    
    public void setResponseTeam(String responseTeam) {
        this.responseTeam = responseTeam;
    }
    
    public Status getStatus() {
        return status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }
}