package com.fct.visitation.services.interfaces;

import com.fct.visitation.models.dto.ReportRequest;
import org.springframework.core.io.Resource;
import java.time.LocalDate;
import java.util.Map;

public interface ReportService {
    Resource generateReport(ReportRequest request);
    
    // Using String format for dates to match the controller expectations
    Resource generateVisitorReport(String startDate, String endDate, String format);
    Resource generateIncidentReport(String startDate, String endDate, String format);
    Resource generateVehicleReport(String startDate, String endDate, String format);
    Resource generateOccupancyReport(String facilityId, String startDate, String endDate, String format);
    
    // Added methods needed by ReportController
    Map<String, Object> getVisitationStatistics();
    Map<String, Object> getSecurityStatistics();
    Map<String, Object> getParkingStatistics();
    
    Map<String, Object> generateVisitorReport(LocalDate startDate, LocalDate endDate);
    Map<String, Object> generateSecurityReport(LocalDate startDate, LocalDate endDate);
    Map<String, Object> generateParkingReport(LocalDate startDate, LocalDate endDate);
    Map<String, Object> generateFacilityReport(LocalDate startDate, LocalDate endDate);
    
    Resource generateExcelReport(String reportType, String startDate, String endDate);
    Resource generatePdfReport(String reportType, String startDate, String endDate);
}