package com.fct.visitation.models.dto;

public class CheckpointScanResponse {
    private boolean success;
    private String message;
    private String visitorName;
    private String checkpointName;
    private String timestamp;
    private String visitorStatus;
    private String visitorPurpose;
    private String destinationFacility;
    private String officerToMeet;
    private boolean hasVehicle;
    private String vehicleInfo;
    
    // Constructors
    public CheckpointScanResponse() {
    }
    
    public CheckpointScanResponse(boolean success, String message, String visitorName, 
                                String checkpointName, String timestamp, String visitorStatus) {
        this.success = success;
        this.message = message;
        this.visitorName = visitorName;
        this.checkpointName = checkpointName;
        this.timestamp = timestamp;
        this.visitorStatus = visitorStatus;
    }
    
    // Getters and Setters
    public boolean isSuccess() {
        return success;
    }
    
    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getVisitorName() {
        return visitorName;
    }
    
    public void setVisitorName(String visitorName) {
        this.visitorName = visitorName;
    }
    
    public String getCheckpointName() {
        return checkpointName;
    }
    
    public void setCheckpointName(String checkpointName) {
        this.checkpointName = checkpointName;
    }
    
    public String getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    
    public String getVisitorStatus() {
        return visitorStatus;
    }
    
    public void setVisitorStatus(String visitorStatus) {
        this.visitorStatus = visitorStatus;
    }
    
    public String getVisitorPurpose() {
        return visitorPurpose;
    }
    
    public void setVisitorPurpose(String visitorPurpose) {
        this.visitorPurpose = visitorPurpose;
    }
    
    public String getDestinationFacility() {
        return destinationFacility;
    }
    
    public void setDestinationFacility(String destinationFacility) {
        this.destinationFacility = destinationFacility;
    }
    
    public String getOfficerToMeet() {
        return officerToMeet;
    }
    
    public void setOfficerToMeet(String officerToMeet) {
        this.officerToMeet = officerToMeet;
    }
    
    public boolean isHasVehicle() {
        return hasVehicle;
    }
    
    public void setHasVehicle(boolean hasVehicle) {
        this.hasVehicle = hasVehicle;
    }
    
    public String getVehicleInfo() {
        return vehicleInfo;
    }
    
    public void setVehicleInfo(String vehicleInfo) {
        this.vehicleInfo = vehicleInfo;
    }
}