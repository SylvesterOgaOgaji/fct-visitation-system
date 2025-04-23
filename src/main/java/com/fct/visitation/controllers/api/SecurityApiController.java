package com.fct.visitation.controllers.api;

import com.fct.visitation.models.dto.AlertResponse;
import com.fct.visitation.models.dto.IncidentAlertRequest;
import com.fct.visitation.models.entity.IncidentAlert;
import com.fct.visitation.repositories.IncidentAlertRepository;
import com.fct.visitation.services.interfaces.NotificationService;
import com.fct.visitation.services.interfaces.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/security")
public class SecurityApiController {
    
    @Autowired
    private SecurityService securityService;
    
    @Autowired
    private NotificationService notificationService;
    
    @Autowired
    private IncidentAlertRepository incidentAlertRepository;
    
    @PostMapping("/alerts")
    @PreAuthorize("hasAnyRole('SECURITY_PERSONNEL', 'ADMIN')")
    public ResponseEntity<AlertResponse> createAlert(@RequestBody IncidentAlertRequest request) {
        try {
            IncidentAlert alert = new IncidentAlert();
            alert.setTitle(request.getTitle());
            alert.setDescription(request.getDescription());
            alert.setLocation(request.getLocation());
            alert.setSeverity(request.getSeverity());
            alert.setReportedAt(LocalDateTime.now());
            alert.setStatus(IncidentAlert.Status.REPORTED);
            
            IncidentAlert savedAlert = incidentAlertRepository.save(alert);
            
            // Send notifications
            if ("HIGH".equals(request.getSeverity())) {
                notificationService.sendEmergencyAlert(savedAlert);
            } else {
                notificationService.sendAlertNotification(savedAlert);
            }
            
            return ResponseEntity.ok(new AlertResponse(true, "Alert created successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new AlertResponse(false, "Error creating alert: " + e.getMessage()));
        }
    }
    
    @GetMapping("/alerts")
    @PreAuthorize("hasAnyRole('SECURITY_PERSONNEL', 'ADMIN')")
    public ResponseEntity<List<IncidentAlert>> getActiveAlerts() {
        List<IncidentAlert> activeAlerts = incidentAlertRepository.findByStatus(IncidentAlert.Status.REPORTED);
        return ResponseEntity.ok(activeAlerts);
    }
}