package com.fct.visitation.controllers.api;

import com.fct.visitation.models.entity.IncidentAlert;
import com.fct.visitation.models.entity.SecurityIncident;
import com.fct.visitation.repositories.IncidentAlertRepository;
import com.fct.visitation.repositories.SecurityIncidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
// Remove the unused import:
// import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/security")
public class SecurityApiController {

    @Autowired
    private SecurityIncidentRepository securityIncidentRepository;

    @Autowired
    private IncidentAlertRepository incidentAlertRepository;

    @GetMapping("/incidents")
    public ResponseEntity<List<SecurityIncident>> getAllIncidents() {
        return ResponseEntity.ok(securityIncidentRepository.findAll());
    }

    @GetMapping("/alerts")
    public ResponseEntity<List<IncidentAlert>> getAllAlerts() {
        return ResponseEntity.ok(incidentAlertRepository.findAll());
    }

    @PostMapping("/incidents")
    public ResponseEntity<SecurityIncident> createIncident(@RequestBody SecurityIncident incident) {
        return ResponseEntity.ok(securityIncidentRepository.save(incident));
    }

    @PostMapping("/alerts")
    public ResponseEntity<IncidentAlert> createAlert(@RequestBody IncidentAlert alert) {
        return ResponseEntity.ok(incidentAlertRepository.save(alert));
    }
    
    // Convert a SecurityIncident to an IncidentAlert
    private IncidentAlert convertToAlert(SecurityIncident incident) {
        IncidentAlert alert = new IncidentAlert();
        alert.setTitle(incident.getTitle() != null ? incident.getTitle() : "");
        alert.setDescription(incident.getDescription() != null ? incident.getDescription() : "");
        
        // Fix: Always provide a valid LocalDateTime
        //alert.setTimestamp(incident.getTimestamp() != null ? incident.getTimestamp() : LocalDateTime.now());
        // Alternative approach
LocalDateTime timestamp;
if (incident.getTimestamp() != null) {
    timestamp = (LocalDateTime) incident.getTimestamp();
} else {
    timestamp = LocalDateTime.now();
}
alert.setTimestamp(timestamp);
        alert.setLocation(incident.getLocation() != null ? incident.getLocation() : "");
        alert.setSeverity(incident.getSeverity() != null ? incident.getSeverity() : "");
        
        // Add these if your IncidentAlert has them
        alert.setReportedAt(LocalDateTime.now());
        alert.setReportedBy("System");
        alert.setStatus(IncidentAlert.Status.PENDING);
        
        return alert;
    }
    
    // Method to handle the issue of type conversion
    @PostMapping("/incidents-to-alerts")
    public ResponseEntity<IncidentAlert> createAlertFromIncident(@RequestBody SecurityIncident incident) {
        SecurityIncident savedIncident = securityIncidentRepository.save(incident);
        IncidentAlert alert = convertToAlert(savedIncident);
        return ResponseEntity.ok(incidentAlertRepository.save(alert));
    }
}