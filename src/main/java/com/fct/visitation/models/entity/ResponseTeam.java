package com.fct.visitation.models.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "response_teams")
public class ResponseTeam {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamId;
    
    @Column(nullable = false, length = 100)
    private String teamName;
    
    @Column(nullable = false, length = 15)
    private String contactNumber;
    
    @Column(length = 100)
    private String email;
    
    // Constructors
    public ResponseTeam() {
    }
    
    public ResponseTeam(Long teamId, String teamName, String contactNumber, String email) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.contactNumber = contactNumber;
        this.email = email;
    }
    
    // Getters and Setters
    public Long getTeamId() {
        return teamId;
    }
    
    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }
    
    public String getTeamName() {
        return teamName;
    }
    
    public void setTeamName(String teamName) {
        this.teamName = teamName;
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
}