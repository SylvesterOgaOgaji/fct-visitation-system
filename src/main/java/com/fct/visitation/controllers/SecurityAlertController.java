package com.fct.visitation.controllers;

import com.fct.visitation.models.dto.AlertResponse;
import com.fct.visitation.models.dto.IncidentAlertRequest;
import com.fct.visitation.models.entity.IncidentAlert;
import com.fct.visitation.models.entity.ResponseTeam;
import com.fct.visitation.models.entity.SecurityIncident;
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
import java.util.stream.Collectors;

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

    // Conversion method to transform SecurityIncident to IncidentAlert
    private IncidentAlert convertToIncidentAlert(SecurityIncident securityIncident) {
        IncidentAlert incidentAlert = new IncidentAlert();
        incidentAlert.setId(securityIncident.getId());
        incidentAlert.setTitle(securityIncident.getEventType());
        incidentAlert.setDescription(securityIncident.getDescription());
        incidentAlert.setLocation(securityIncident.getLocation());
        incidentAlert.setReportedAt(securityIncident.getIncidentTimestamp());
        
        // Map status
        switch (securityIncident.getStatus()) {
            case REPORTED:
                incidentAlert.setStatus(IncidentAlert.Status.REPORTED);
                break;
            case IN_PROGRESS:
                incidentAlert.setStatus(IncidentAlert.Status.IN_PROGRESS);
                break;
            case RESOLVED:
                incidentAlert.setStatus(IncidentAlert.Status.RESOLVED);
                break;
            default:
                incidentAlert.setStatus(IncidentAlert.Status.REPORTED);
        }
        
        return incidentAlert;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('SECURITY_PERSONNEL', 'RESPONSE_TEAM', 'ADMIN')")
    public String alertsDashboard(Model model) {
        // Convert SecurityIncidents to IncidentAlerts
        List<IncidentAlert> activeAlerts = securityService.findActiveIncidents().stream()
            .map(this::convertToIncidentAlert)
            .collect(Collectors.toList());
        
        List<IncidentAlert> resolvedAlerts = activeAlerts; // Placeholder, modify as needed
        List<ResponseTeam> availableTeams = securityService.findAllResponseTeams();
        
        model.addAttribute("activeAlerts", activeAlerts);
        model.addAttribute("resolvedAlerts", resolvedAlerts);
        model.addAttribute("availableTeams", availableTeams);
        model.addAttribute("newAlert", new IncidentAlertRequest());
        
        return "security/alerts";
    }

    // Existing methods remain the same, but use the conversion method where needed
    @GetMapping("/api/all")
    @PreAuthorize("hasAnyRole('SECURITY_PERSONNEL', 'RESPONSE_TEAM', 'ADMIN')")
    @ResponseBody
    public ResponseEntity<List<IncidentAlert>> getAllAlertsApi() {
        List<IncidentAlert> allAlerts = securityService.findActiveIncidents().stream()
            .map(this::convertToIncidentAlert)
            .collect(Collectors.toList());
        return ResponseEntity.ok(allAlerts);
    }

    // Other methods remain the same, just ensure any SecurityIncident is converted to IncidentAlert
}