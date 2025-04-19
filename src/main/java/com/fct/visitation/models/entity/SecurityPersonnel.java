package com.fct.visitation.models.entity;

import com.fct.visitation.models.enums.Role;
import jakarta.persistence.*;

@Entity
@Table(name = "security_personnel")
public class SecurityPersonnel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    private String username;
    
    private String passwordHash;

    @Column(name = "`rank`")
    private String rank;
    
    private String badgeNumber;
    
    private String department;
    
    private String email;
    
    private String phone;
    
    @Enumerated(EnumType.STRING)
    private Role role;
    
    private boolean active;
    
    // Constructors
    public SecurityPersonnel() {
    }
    
    public SecurityPersonnel(Long id, String name, String username, String passwordHash, 
                          String rank, String badgeNumber, String department, String email, 
                          String phone, Role role, boolean active) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.passwordHash = passwordHash;
        this.rank = rank;
        this.badgeNumber = badgeNumber;
        this.department = department;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.active = active;
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
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPasswordHash() {
        return passwordHash;
    }
    
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
    
    public String getRank() {
        return rank;
    }
    
    public void setRank(String rank) {
        this.rank = rank;
    }
    
    public String getBadgeNumber() {
        return badgeNumber;
    }
    
    public void setBadgeNumber(String badgeNumber) {
        this.badgeNumber = badgeNumber;
    }
    
    public String getDepartment() {
        return department;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public Role getRole() {
        return role;
    }
    
    public void setRole(Role role) {
        this.role = role;
    }
    
    public boolean isActive() {
        return active;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
}