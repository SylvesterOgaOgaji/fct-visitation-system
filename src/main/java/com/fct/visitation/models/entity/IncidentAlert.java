package com.fct.visitation.models.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "incident_alerts")
public class IncidentAlert {
    
    public enum Status { REPORTED, IN_PROGRESS, RESOLVED }
    public enum SeverityLevel { LOW, MEDIUM, HIGH, CRITICAL }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title;
    
    @Lob
    private String description;
    
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @Column(nullable = false)
    private LocalDateTime reportedAt;
    
    private String location;
    
    @Enumerated(EnumType.STRING)
    private SeverityLevel severity;
    
    @Enumerated(EnumType.STRING)
    private Status status = Status.REPORTED;

    @ManyToOne
    @JoinColumn(name = "reporter_id")
    private SecurityPersonnel reporter;

    @ManyToOne
    @JoinColumn(name = "response_team_id")
    private ResponseTeam responseTeam;

    @ManyToOne
    @JoinColumn(name = "visitor_id")
    private Visitor visitor;

    public void setReportedAt(LocalDateTime reportedAt) {
        this.reportedAt = reportedAt;
    }
}