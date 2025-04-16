package com.fct.visitation.controllers;

import com.fct.visitation.models.dto.AlertResponse;
import com.fct.visitation.models.dto.IncidentAlertRequest;
import com.fct.visitation.models.entity.IncidentAlert;
import com.fct.visitation.models.entity.ResponseTeam;
import com.fct.visitation.models.entity.SecurityPersonnel;
import com.fct.visitation.services.interfaces.NotificationService;
import com.fct.visitation.services.interfaces.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Controller for managing security alerts and incidents
 */
@Controller
@RequestMapping("/security/alerts")
public class SecurityAlertController {

    private final SecurityService securityService;
    private final NotificationService notificationService;

    @Autowired
    public SecurityAlertController(
            SecurityService securityService,
            NotificationService notificationService) {
        this.securityService = securityService;
        this.notificationService = notificationService;
    }

    /**
     * Display the alerts dashboard page for security personnel
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('SECURITY_PERSONNEL', 'RESPONSE_TEAM', 'ADMIN')")
    public String alertsDashboard(Model model) {
        List<IncidentAlert> activeAlerts = securityService.getActiveAlerts();
        List<IncidentAlert> resolvedAlerts = securityService.getResolvedAlerts();
        List<ResponseTeam> availableTeams = securityService.getAvailableResponseTeams();
        
        model.addAttribute("activeAlerts", activeAlerts);
        model.addAttribute("resolvedAlerts", resolvedAlerts);
        model.addAttribute("availableTeams", availableTeams);
        model.addAttribute("newAlert", new IncidentAlertRequest());
        
        return "security/alerts";
    }

    /**
     * Create a new security alert
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('SECURITY_PERSONNEL', 'ADMIN')")
    public ResponseEntity<AlertResponse> createAlert(
            @RequestBody IncidentAlertRequest alertRequest,
            Principal principal) {
        
        try {
            // Get the security personnel creating the alert
            SecurityPersonnel reporter = securityService.getSecurityPersonnelByUsername(principal.getName());
            
            if (reporter == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new AlertResponse(false, "Invalid security personnel"));
            }
            
            // Create and save the alert
            IncidentAlert alert = new IncidentAlert();
            alert.setTitle(alertRequest.getTitle());
            alert.setDescription(alertRequest.getDescription());
            alert.setLocation(alertRequest.getLocation());
            alert.setSeverity(alertRequest.getSeverity());
            alert.setStatus("ACTIVE");
            alert.setReportedBy(reporter);
            alert.setReportedAt(LocalDateTime.now());
            
            // Set the response team if specified
            if (alertRequest.getResponseTeamId() != null) {
                ResponseTeam team = securityService.getResponseTeamById(alertRequest.getResponseTeamId());
                if (team != null) {
                    alert.setAssignedTeam(team);
                }
            }
            
            // Save the alert
            IncidentAlert savedAlert = securityService.saveIncidentAlert(alert);
            
            // Send notifications based on severity
            if ("HIGH".equals(alertRequest.getSeverity())) {
                // Send SMS to all response teams for high severity alerts
                notificationService.sendEmergencyAlert(savedAlert);
            } else {
                // Send in-app notifications for medium and low severity alerts
                notificationService.sendAlertNotification(savedAlert);
            }
            
            // Log the security event
            securityService.logSecurityEvent(
                "Security Alert Created", 
                "Alert: " + alertRequest.getTitle() + ", Severity: " + alertRequest.getSeverity(),
                alertRequest.getSeverity()
            );
            
            return ResponseEntity.ok(new AlertResponse(true, "Alert created successfully"));
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AlertResponse(false, "Error creating alert: " + e.getMessage()));
        }
    }

    /**
     * Update the status of an alert
     */
    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('SECURITY_PERSONNEL', 'RESPONSE_TEAM', 'ADMIN')")
    public ResponseEntity<AlertResponse> updateAlertStatus(
            @PathVariable Long id,
            @RequestParam String status,
            @RequestParam(required = false) String resolutionNotes,
            Principal principal) {
        
        try {
            IncidentAlert alert = securityService.getIncidentAlertById(id);
            
            if (alert == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new AlertResponse(false, "Alert not found"));
            }
            
            // Update the alert status
            alert.setStatus(status);
            
            if ("RESOLVED".equals(status)) {
                alert.setResolvedAt(LocalDateTime.now());
                alert.setResolvedBy(securityService.getSecurityPersonnelByUsername(principal.getName()));
                alert.setResolutionNotes(resolutionNotes);
            }
            
            securityService.saveIncidentAlert(alert);
            
            // Send notification about status change
            notificationService.sendAlertStatusUpdateNotification(alert);
            
            return ResponseEntity.ok(new AlertResponse(true, "Alert status updated successfully"));
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AlertResponse(false, "Error updating alert status: " + e.getMessage()));
        }
    }

    /**
     * Assign a response team to an alert
     */
    @PutMapping("/{id}/assign")
    @PreAuthorize("hasAnyRole('SECURITY_PERSONNEL', 'ADMIN')")
    public ResponseEntity<AlertResponse> assignResponseTeam(
            @PathVariable Long id,
            @RequestParam Long teamId) {
        
        try {
            IncidentAlert alert = securityService.getIncidentAlertById(id);
            ResponseTeam team = securityService.getResponseTeamById(teamId);
            
            if (alert == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new AlertResponse(false, "Alert not found"));
            }
            
            if (team == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new AlertResponse(false, "Response team not found"));
            }
            
            // Assign the team
            alert.setAssignedTeam(team);
            securityService.saveIncidentAlert(alert);
            
            // Notify the team
            notificationService.sendTeamAssignmentNotification(alert, team);
            
            return ResponseEntity.ok(new AlertResponse(true, "Response team assigned successfully"));
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AlertResponse(false, "Error assigning response team: " + e.getMessage()));
        }
    }

    /**
     * Get alert details
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('SECURITY_PERSONNEL', 'RESPONSE_TEAM', 'ADMIN')")
    public String getAlertDetails(@PathVariable Long id, Model model) {
        IncidentAlert alert = securityService.getIncidentAlertById(id);
        
        if (alert == null) {
            return "redirect:/security/alerts";
        }
        
        List<ResponseTeam> availableTeams = securityService.getAvailableResponseTeams();
        
        model.addAttribute("alert", alert);
        model.addAttribute("availableTeams", availableTeams);
        
        return "security/alert-details";
    }

    /**
     * Get all alerts as JSON (for API)
     */
    @GetMapping("/api/all")
    @PreAuthorize("hasAnyRole('SECURITY_PERSONNEL', 'RESPONSE_TEAM', 'ADMIN')")
    @ResponseBody
    public ResponseEntity<List<IncidentAlert>> getAllAlertsApi() {
        List<IncidentAlert> allAlerts = securityService.getAllAlerts();
        return ResponseEntity.ok(allAlerts);
    }
}