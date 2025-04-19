package com.fct.visitation.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParkingResponse {
    private String allocationId;
    private String spaceNumber;
    private String parkingArea;
    private String status;
    private String message;
    private boolean success;
    
    // Constructor used in controllers
    public ParkingResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}