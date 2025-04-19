package com.fct.visitation.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlertResponse {
    private String alertId;
    private String message;
    private String status;
    private String timestamp;
    private boolean success;
    
    // Constructor for simple responses
    public AlertResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
        this.timestamp = java.time.LocalDateTime.now().toString();
    }
}