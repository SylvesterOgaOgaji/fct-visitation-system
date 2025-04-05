package com.fct.visitation.models.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "facilities")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Facility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long facilityId;

    @Column(nullable = false, length = 100)
    private String facilityName;

    @Column(nullable = false, length = 255)
    private String address;

    @Column(nullable = false, length = 15)
    private String contactNumber;

    @Column(length = 100)
    private String email;

    // Explicit getters and setters
    public Long getFacilityId() { return this.facilityId; }
    public void setFacilityId(Long facilityId) { this.facilityId = facilityId; }

    public String getFacilityName() { return this.facilityName; }
    public void setFacilityName(String facilityName) { this.facilityName = facilityName; }

    public String getAddress() { return this.address; }
    public void setAddress(String address) { this.address = address; }

    public String getContactNumber() { return this.contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    public String getEmail() { return this.email; }
    public void setEmail(String email) { this.email = email; }
}
