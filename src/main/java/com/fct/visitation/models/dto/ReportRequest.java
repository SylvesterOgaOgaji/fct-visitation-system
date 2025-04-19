package com.fct.visitation.models.dto;

public class ReportRequest {
    private String reportType;
    private String startDate;
    private String endDate;
    private String facilityId;
    private String format; // "PDF", "CSV", "EXCEL"
    
    // Constructors
    public ReportRequest() {
    }
    
    public ReportRequest(String reportType, String startDate, String endDate, String facilityId, String format) {
        this.reportType = reportType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.facilityId = facilityId;
        this.format = format;
    }
    
    // Getters and Setters
    public String getReportType() {
        return reportType;
    }
    
    public void setReportType(String reportType) {
        this.reportType = reportType;
    }
    
    public String getStartDate() {
        return startDate;
    }
    
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    
    public String getEndDate() {
        return endDate;
    }
    
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    
    public String getFacilityId() {
        return facilityId;
    }
    
    public void setFacilityId(String facilityId) {
        this.facilityId = facilityId;
    }
    
    public String getFormat() {
        return format;
    }
    
    public void setFormat(String format) {
        this.format = format;
    }
}