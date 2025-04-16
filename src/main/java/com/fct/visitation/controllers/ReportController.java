package com.fct.visitation.controllers;

import com.fct.visitation.models.dto.ReportRequest;
import com.fct.visitation.services.interfaces.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

/**
 * Controller for generating and managing reports
 */
@Controller
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    /**
     * Display the reports dashboard
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'OFFICER')")
    public String reportsDashboard(Model model) {
        // Get high-level statistics for dashboard
        Map<String, Object> visitationStats = reportService.getVisitationStatistics();
        Map<String, Object> securityStats = reportService.getSecurityStatistics();
        Map<String, Object> parkingStats = reportService.getParkingStatistics();
        
        model.addAttribute("visitationStats", visitationStats);
        model.addAttribute("securityStats", securityStats);
        model.addAttribute("parkingStats", parkingStats);
        model.addAttribute("reportRequest", new ReportRequest());
        
        return "admin/reports";
    }

    /**
     * Generate visitor report based on date range
     */
    @GetMapping("/visitors")
    @PreAuthorize("hasAnyRole('ADMIN', 'OFFICER')")
    public String visitorReportPage(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            Model model) {
        
        // Use default date range if not provided
        if (startDate == null) {
            startDate = LocalDate.now().minusMonths(1);
        }
        
        if (endDate == null) {
            endDate = LocalDate.now();
        }
        
        Map<String, Object> reportData = reportService.generateVisitorReport(startDate, endDate);
        
        model.addAttribute("reportData", reportData);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        
        return "admin/visitor-report";
    }

    /**
     * Generate security incident report based on date range
     */
    @GetMapping("/security")
    @PreAuthorize("hasAnyRole('ADMIN', 'SECURITY_PERSONNEL')")
    public String securityReportPage(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            Model model) {
        
        // Use default date range if not provided
        if (startDate == null) {
            startDate = LocalDate.now().minusMonths(1);
        }
        
        if (endDate == null) {
            endDate = LocalDate.now();
        }
        
        Map<String, Object> reportData = reportService.generateSecurityReport(startDate, endDate);
        
        model.addAttribute("reportData", reportData);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        
        return "admin/security-report";
    }

    /**
     * Generate parking usage report based on date range
     */
    @GetMapping("/parking")
    @PreAuthorize("hasAnyRole('ADMIN', 'SECURITY_PERSONNEL')")
    public String parkingReportPage(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            Model model) {
        
        // Use default date range if not provided
        if (startDate == null) {
            startDate = LocalDate.now().minusMonths(1);
        }
        
        if (endDate == null) {
            endDate = LocalDate.now();
        }
        
        Map<String, Object> reportData = reportService.generateParkingReport(startDate, endDate);
        
        model.addAttribute("reportData", reportData);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        
        return "admin/parking-report";
    }

    /**
     * Generate facility usage report based on date range
     */
    @GetMapping("/facilities")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public String facilityReportPage(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            Model model) {
        
        // Use default date range if not provided
        if (startDate == null) {
            startDate = LocalDate.now().minusMonths(1);
        }
        
        if (endDate == null) {
            endDate = LocalDate.now();
        }
        
        Map<String, Object> reportData = reportService.generateFacilityReport(startDate, endDate);
        
        model.addAttribute("reportData", reportData);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        
        return "admin/facility-report";
    }

    /**
     * Export report as Excel
     */
    @PostMapping("/export/excel")
    @PreAuthorize("hasAnyRole('ADMIN', 'OFFICER', 'SECURITY_PERSONNEL')")
    public ResponseEntity<Resource> exportReportAsExcel(@RequestBody ReportRequest request) {
        try {
            Resource excelResource = reportService.generateExcelReport(
                    request.getReportType(), 
                    request.getStartDate(), 
                    request.getEndDate());
            
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, 
                            "attachment; filename=" + request.getReportType() + "-report.xlsx")
                    .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .body(excelResource);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Export report as PDF
     */
    @PostMapping("/export/pdf")
    @PreAuthorize("hasAnyRole('ADMIN', 'OFFICER', 'SECURITY_PERSONNEL')")
    public ResponseEntity<Resource> exportReportAsPdf(@RequestBody ReportRequest request) {
        try {
            Resource pdfResource = reportService.generatePdfReport(
                    request.getReportType(), 
                    request.getStartDate(), 
                    request.getEndDate());
            
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, 
                            "attachment; filename=" + request.getReportType() + "-report.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfResource);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Get report data as JSON (for API or AJAX calls)
     */
    @GetMapping("/api/data")
    @PreAuthorize("hasAnyRole('ADMIN', 'OFFICER', 'SECURITY_PERSONNEL')")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getReportData(
            @RequestParam String reportType,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        
        Map<String, Object> reportData;
        
        switch (reportType) {
            case "visitor":
                reportData = reportService.generateVisitorReport(startDate, endDate);
                break;
            case "security":
                reportData = reportService.generateSecurityReport(startDate, endDate);
                break;
            case "parking":
                reportData = reportService.generateParkingReport(startDate, endDate);
                break;
            case "facility":
                reportData = reportService.generateFacilityReport(startDate, endDate);
                break;
            default:
                return ResponseEntity.badRequest().build();
        }
        
        return ResponseEntity.ok(reportData);
    }
}