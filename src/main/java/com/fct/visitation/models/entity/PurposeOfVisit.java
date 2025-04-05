package com.fct.visitation.models.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "purpose_of_visit")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurposeOfVisit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long purposeId;

    @ManyToOne
    @JoinColumn(name = "facility_id", nullable = false)
    private Facility facility;

    @Column(nullable = false, length = 100)
    private String description;

    // Explicit getters and setters
    public Long getPurposeId() { return this.purposeId; }
    public void setPurposeId(Long purposeId) { this.purposeId = purposeId; }

    public Facility getFacility() { return this.facility; }
    public void setFacility(Facility facility) { this.facility = facility; }

    public String getDescription() { return this.description; }
    public void setDescription(String description) { this.description = description; }
}
