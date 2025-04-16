package com.fct.visitation.models.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity representing a security incident or alert
 */
@Entity
@Table(name = "incident_alerts")
public class IncidentAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "title", nullable = false, length = 100)
    private String title;
    
    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "location", nullable = false, length = 100)
    private String location;
    
    @Column(name = "severity", nullable = false, length = 20)
    private String severity; // HIGH, MEDIUM, LOW
    
    @Column(name = "status", nullable = false, length = 20)
    private String status = "ACTIVE"; // ACTIVE, IN_PROGRESS, RESOLVED, CLOSED
    
    @ManyToOne
    @JoinColumn(name = "reported_by_id")
    private SecurityPersonnel reportedBy;
    
    @Column(name = "reported_at", nullable = false)
    private LocalDateTime reportedAt;
    
    @ManyToOne
    @JoinColumn(name = "assigned_team_id")
    private ResponseTeam assignedTeam;
    
    @Column(name = "assigned_at")
    private LocalDateTime assignedAt;
    
    @ManyToOne
    @JoinColumn(name = "resolved_by_id")
    private SecurityPersonnel resolvedBy;
    
    @Column(name = "resolved_at")
    private LocalDateTime resolvedAt;
    
    @Column(name = "resolution_notes", columnDefinition = "TEXT")
    private String resolutionNotes;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (reportedAt == null) {
            reportedAt = LocalDateTime.now();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public SecurityPersonnel getReportedBy() {
        return reportedBy;
    }

    public void setReportedBy(SecurityPersonnel reportedBy) {
        this.reportedBy = reportedBy;
    }

    public LocalDateTime getReportedAt() {
        return reportedAt;
    }

    public void setReportedAt(LocalDateTime reportedAt) {
        this.reportedAt = reportedAt;
    }

    public ResponseTeam getAssignedTeam() {
        return assignedTeam;
    }

    public void setAssignedTeam(ResponseTeam assignedTeam) {
        this.assignedTeam = assignedTeam;
        if (assignedTeam != null && assignedAt == null) {
            this.assignedAt = LocalDateTime.now();
        }
    }

    public LocalDateTime getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(LocalDateTime assignedAt) {
        this.assignedAt = assignedAt;
    }

    public SecurityPersonnel getResolvedBy() {
        return resolvedBy;
    }

    public void setResolvedBy(SecurityPersonnel resolvedBy) {
        this.resolvedBy = resolvedBy;
        if (resolvedBy != null && resolvedAt == null) {
            this.resolvedAt = LocalDateTime.now();
        }
    }

    public LocalDateTime getResolvedAt() {
        return resolvedAt;
    }

    public void setResolvedAt(LocalDateTime resolvedAt) {
        this.resolvedAt = resolvedAt;
    }

    public String getResolutionNotes() {
        return resolutionNotes;
    }

    public void setResolutionNotes(String resolutionNotes) {
        this.resolutionNotes = resolutionNotes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    /**
     * Check if this alert is active
     * 
     * @return true if the alert is active, false otherwise
     */
    @Transient
    public boolean isActive() {
        return "ACTIVE".equals(status) || "IN_PROGRESS".equals(status);
    }
    
    /**
     * Check if this alert is resolved
     * 
     * @return true if the alert is resolved, false otherwise
     */
    @Transient
    public boolean isResolved() {
        return "RESOLVED".equals(status) || "CLOSED".equals(status);
    }
    
    /**
     * Get the time elapsed since the alert was reported
     * 
     * @return Time elapsed in minutes
     */
    @Transient
    public long getTimeElapsedMinutes() {
        LocalDateTime end = isResolved() ? resolvedAt : LocalDateTime.now();
        return java.time.Duration.between(reportedAt, end).toMinutes();
    }
    
    // toString method
    @Override
    public String toString() {
        return "IncidentAlert{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", severity='" + severity + '\'' +
                ", status='" + status + '\'' +
                ", reportedAt=" + reportedAt +
                ", location='" + location + '\'' +
                '}';
    }
}