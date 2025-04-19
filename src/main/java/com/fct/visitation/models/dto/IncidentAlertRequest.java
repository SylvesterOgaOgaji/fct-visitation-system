package com.fct.visitation.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncidentAlertRequest {
    private String visitorId;
    private String title;
    private String location;
    private String description;
    private String responseTeamId;
    private String severity;
    
    // Explicit getter methods to ensure they're available
    public String getTitle() {
        return title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public String getLocation() {
        return location;
    }
    
    public String getResponseTeamId() {
        return responseTeamId;
    }
    
    public String getSeverity() {
        return severity;
    }
}