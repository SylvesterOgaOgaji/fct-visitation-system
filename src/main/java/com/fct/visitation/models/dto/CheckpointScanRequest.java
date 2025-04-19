package com.fct.visitation.models.dto;

public class CheckpointScanRequest {
    private String visitorId;
    private String checkpointId;
    private String qrCode;
    private String scanType; // "ENTRY" or "EXIT"
    private String timestamp;
    private String notes;
    
    // Constructors
    public CheckpointScanRequest() {
    }
    
    public CheckpointScanRequest(String visitorId, String checkpointId, String qrCode, String scanType, String timestamp, String notes) {
        this.visitorId = visitorId;
        this.checkpointId = checkpointId;
        this.qrCode = qrCode;
        this.scanType = scanType;
        this.timestamp = timestamp;
        this.notes = notes;
    }
    
    // Getters and Setters
    public String getVisitorId() {
        return visitorId;
    }
    
    public void setVisitorId(String visitorId) {
        this.visitorId = visitorId;
    }
    
    public String getCheckpointId() {
        return checkpointId;
    }
    
    public void setCheckpointId(String checkpointId) {
        this.checkpointId = checkpointId;
    }
    
    public String getQrCode() {
        return qrCode;
    }
    
    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }
    
    public String getScanType() {
        return scanType;
    }
    
    public void setScanType(String scanType) {
        this.scanType = scanType;
    }
    
    public String getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
}