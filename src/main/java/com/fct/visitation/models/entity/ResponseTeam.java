package com.fct.visitation.models.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Entity representing a security response team for handling security incidents
 */
@Entity
@Table(name = "response_teams")
public class ResponseTeam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    
    @Column(name = "code", nullable = false, length = 20, unique = true)
    private String code;
    
    @Column(name = "description", length = 255)
    private String description;
    
    @Column(name = "team_leader", length = 100)
    private String teamLeader;
    
    @Column(name = "contact_number", length = 20)
    private String contactNumber;
    
    @Column(name = "email", length = 100)
    private String email;
    
    @Column(name = "location", length = 100)
    private String location;
    
    @Column(name = "availability_status", length = 20, nullable = false)
    private String availabilityStatus = "AVAILABLE"; // AVAILABLE, BUSY, OFF_DUTY
    
    @OneToMany(mappedBy = "assignedTeam")
    private Set<IncidentAlert> assignedAlerts;
    
    @ManyToMany
    @JoinTable(
        name = "response_team_members",
        joinColumns = @JoinColumn(name = "team_id"),
        inverseJoinColumns = @JoinColumn(name = "security_id")
    )
    private Set<SecurityPersonnel> members;
    
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTeamLeader() {
        return teamLeader;
    }

    public void setTeamLeader(String teamLeader) {
        this.teamLeader = teamLeader;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(String availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public Set<IncidentAlert> getAssignedAlerts() {
        return assignedAlerts;
    }

    public void setAssignedAlerts(Set<IncidentAlert> assignedAlerts) {
        this.assignedAlerts = assignedAlerts;
    }

    public Set<SecurityPersonnel> getMembers() {
        return members;
    }

    public void setMembers(Set<SecurityPersonnel> members) {
        this.members = members;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
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
     * Get the number of active members in the team
     * 
     * @return Count of active members
     */
    @Transient
    public int getActiveMembers() {
        if (members == null) {
            return 0;
        }
        return (int) members.stream()
                .filter(SecurityPersonnel::getIsActive)
                .count();
    }
    
    /**
     * Check if the team is available for assignment
     * 
     * @return true if the team is available, false otherwise
     */
    @Transient
    public boolean isAvailable() {
        return isActive && "AVAILABLE".equals(availabilityStatus);
    }
    
    // toString method
    @Override
    public String toString() {
        return "ResponseTeam{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", teamLeader='" + teamLeader + '\'' +
                ", availabilityStatus='" + availabilityStatus + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}