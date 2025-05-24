package com.fct.visitation.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class SecurityIncidentRequest {
    private String eventType;
    
    @NotBlank(message = "Description cannot be blank")
    private String description;
    
    @NotBlank(message = "Incident type cannot be blank")
    private String incidentType;
    
    @NotNull(message = "Security personnel ID is required")
    private Long securityPersonnelId;
    
    @NotNull(message = "Response team ID is required")
    private Long responseTeamId;
    
    @NotBlank(message = "Location cannot be blank")
    private String location;
    
    @NotNull(message = "Timestamp is required")
    private LocalDateTime incidentTimestamp;
}