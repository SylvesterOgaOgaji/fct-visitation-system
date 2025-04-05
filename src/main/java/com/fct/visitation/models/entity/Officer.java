package com.fct.visitation.models.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "officers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Officer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long officerId;

    @ManyToOne
    @JoinColumn(name = "facility_id", nullable = false)
    private Facility facility;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 50)
    private String position;

    @Column(nullable = false, length = 50)
    private String department;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DutyStatus dutyStatus = DutyStatus.ON_DUTY;

    @Column(length = 100)
    private String email;

    @Column(length = 15)
    private String phoneNumber;

    // Explicit getters and setters
    public Long getOfficerId() { return this.officerId; }
    public void setOfficerId(Long officerId) { this.officerId = officerId; }

    public Facility getFacility() { return this.facility; }
    public void setFacility(Facility facility) { this.facility = facility; }

    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }

    public String getPosition() { return this.position; }
    public void setPosition(String position) { this.position = position; }

    public String getDepartment() { return this.department; }
    public void setDepartment(String department) { this.department = department; }

    public DutyStatus getDutyStatus() { return this.dutyStatus; }
    public void setDutyStatus(DutyStatus dutyStatus) { this.dutyStatus = dutyStatus; }

    public String getEmail() { return this.email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return this.phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public enum DutyStatus {
        ON_DUTY, OFF_DUTY
    }
}
