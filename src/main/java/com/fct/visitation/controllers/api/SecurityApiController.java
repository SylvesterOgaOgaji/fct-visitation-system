package com.fct.visitation.controllers.api;

import com.fct.visitation.models.dto.SecurityIncidentRequest;
import com.fct.visitation.models.enums.VehicleType; 
import com.fct.visitation.models.entity.IncidentAlert;
import com.fct.visitation.models.entity.SecurityIncident;
import com.fct.visitation.services.interfaces.SecurityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/security")
public class SecurityApiController {

    private final SecurityService securityService;

    public SecurityApiController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @GetMapping("/incidents")
    public ResponseEntity<List<SecurityIncident>> getActiveIncidents() {
        return ResponseEntity.ok(securityService.findActiveIncidents());
    }

    @PostMapping("/incidents")
    public ResponseEntity<SecurityIncident> createIncident(@RequestBody SecurityIncidentRequest request) {
        SecurityIncident incident = securityService.logSecurityEvent(
                request.getEventType(),
                request.getDescription(),
                request.getLocation()
        );
        return ResponseEntity.ok(incident);
    }
}