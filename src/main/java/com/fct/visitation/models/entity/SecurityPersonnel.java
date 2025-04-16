package com.fct.visitation.models.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Entity representing security personnel working at FCT facilities
 */
@Entity
@Table(name = "security_personnel")
public class SecurityPersonnel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;
    
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;
    
    @Column(name = "badge_number", nullable = false, length = 50, unique = true)
    private String badgeNumber;
    
    @Column(name = "rank", length = 50)
    private String rank;
    
    @Column(name = "email", length = 100, unique = true)
    private String email;
    
    @Column(name = "phone_number", length = 20)
    private String phoneNumber;
    
    @Column(name = "nin", length = 20, unique = true)
    private String nin;
    
    @ManyToOne
    @JoinColumn(name = "facility_id")
    private Facility facility;
    
    @Column(name = "position", length = 50)
    private String position;
    
    @Column(name = "shift", length = 20)
    private String shift; // MORNING, AFTERNOON, NIGHT
    
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
    
    @Column(name = "username", length = 50, unique = true)
    private String username;
    
    @Column(name = "password", length = 255)
    private String password;
    
    @Column(name = "last_login")
    private LocalDateTime lastLogin;
    
    @ManyToMany(mappedBy = "members")
    private Set<ResponseTeam> teams;
    
    @OneToMany(mappedBy = "reportedBy")
    private Set<IncidentAlert> reportedAlerts;
    
    @OneToMany(mappedBy = "resolvedBy")
    private Set<IncidentAlert> resolvedAlerts;
    
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBadgeNumber() {
        return badgeNumber;
    }

    public void setBadgeNumber(String badgeNumber) {
        this.badgeNumber = badgeNumber;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNin() {
        return nin;
    }

    public void setNin(String nin) {
        this.nin = nin;
    }

    public Facility getFacility() {
        return facility;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Set<ResponseTeam> getTeams() {
        return teams;
    }

    public void setTeams(Set<ResponseTeam> teams) {
        this.teams = teams;
    }

    public Set<IncidentAlert> getReportedAlerts() {
        return reportedAlerts;
    }

    public void setReportedAlerts(Set<IncidentAlert> reportedAlerts) {
        this.reportedAlerts = reportedAlerts;
    }

    public Set<IncidentAlert> getResolvedAlerts() {
        return resolvedAlerts;
    }

    public void setResolvedAlerts(Set<IncidentAlert> resolvedAlerts) {
        this.resolvedAlerts = resolvedAlerts;
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
     * Get the full name of the security personnel
     * 
     * @return Full name (first name + last name)
     */
    @Transient
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    /**
     * Check if the security personnel is on duty based on shift
     * 
     * @return true if on duty, false otherwise
     */
    @Transient
    public boolean isOnDuty() {
        if (shift == null) {
            return false;
        }
        
        int hour = LocalDateTime.now().getHour();
        
        return switch (shift) {
            case "MORNING" -> hour >= 6 && hour < 14;
            case "AFTERNOON" -> hour >= 14 && hour < 22;
            case "NIGHT" -> hour >= 22 || hour < 6;
            default -> false;
        };
    }
    
    // toString method
    @Override
    public String toString() {
        return "SecurityPersonnel{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", badgeNumber='" + badgeNumber + '\'' +
                ", rank='" + rank + '\'' +
                ", position='" + position + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}