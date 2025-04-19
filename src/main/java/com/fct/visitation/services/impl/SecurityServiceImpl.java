package com.fct.visitation.services.impl;

import com.fct.visitation.models.entity.SecurityIncident;
import com.fct.visitation.models.entity.SecurityPersonnel;
import com.fct.visitation.models.entity.ResponseTeam;
import com.fct.visitation.models.entity.IncidentAlert;
import com.fct.visitation.repositories.SecurityIncidentRepository;
import com.fct.visitation.repositories.SecurityPersonnelRepository;
import com.fct.visitation.repositories.ResponseTeamRepository;
import com.fct.visitation.services.interfaces.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SecurityServiceImpl implements SecurityService {

    private final SecurityIncidentRepository securityIncidentRepository;
    private final SecurityPersonnelRepository securityPersonnelRepository;
    private final ResponseTeamRepository responseTeamRepository;

    @Autowired
    public SecurityServiceImpl(
            SecurityIncidentRepository securityIncidentRepository,
            SecurityPersonnelRepository securityPersonnelRepository,
            ResponseTeamRepository responseTeamRepository) {
        this.securityIncidentRepository = securityIncidentRepository;
        this.securityPersonnelRepository = securityPersonnelRepository;
        this.responseTeamRepository = responseTeamRepository;
    }

    @Override
    @Transactional
    public SecurityIncident logSecurityEvent(String eventType, String description, String location) {
        SecurityIncident incident = new SecurityIncident();
        incident.setEventType(eventType);
        incident.setDescription(description);
        incident.setLocation(location);
        incident.setIncidentTimestamp(LocalDateTime.now());
        incident.setStatus(SecurityIncident.IncidentStatus.REPORTED);
        return securityIncidentRepository.save(incident);
    }

    @Override
    @Transactional
    public SecurityIncident logSecurityEvent(String eventType, String description, Long locationId) {
        return logSecurityEvent(eventType, description, String.valueOf(locationId));
    }

    @Override
    @Transactional
    public SecurityIncident logSecurityEvent(String eventType, String description) {
        return logSecurityEvent(eventType, description, "Unknown Location");
    }

    @Override
    public List<SecurityIncident> findActiveIncidents() {
        return securityIncidentRepository.findByStatus(SecurityIncident.IncidentStatus.REPORTED);
    }

    @Override
    public List<IncidentAlert> findActiveIncidentAlerts() {
        // Convert SecurityIncidents to IncidentAlerts
        return findActiveIncidents().stream()
            .map(this::convertToIncidentAlert)
            .collect(Collectors.toList());
    }

    private IncidentAlert convertToIncidentAlert(SecurityIncident incident) {
        IncidentAlert alert = new IncidentAlert();
        alert.setId(incident.getId());
        alert.setTitle(incident.getEventType());
        alert.setDescription(incident.getDescription());
        alert.setLocation(incident.getLocation());
        alert.setReportedAt(incident.getIncidentTimestamp());
        alert.setStatus(mapIncidentStatus(incident.getStatus()));
        return alert;
    }

    private IncidentAlert.Status mapIncidentStatus(SecurityIncident.IncidentStatus status) {
        switch (status) {
            case REPORTED:
                return IncidentAlert.Status.REPORTED;
            case IN_PROGRESS:
                return IncidentAlert.Status.IN_PROGRESS;
            case RESOLVED:
                return IncidentAlert.Status.RESOLVED;
            default:
                return IncidentAlert.Status.REPORTED;
        }
    }

    @Override
    public List<ResponseTeam> findAllResponseTeams() {
        return responseTeamRepository.findAll();
    }

    @Override
    public List<SecurityPersonnel> findAllSecurityPersonnel() {
        return securityPersonnelRepository.findAll();
    }

    @Override
    public SecurityPersonnel findSecurityPersonnelById(Long id) {
        return securityPersonnelRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Security Personnel not found"));
    }

    @Override
    public SecurityPersonnel findSecurityPersonnelByUsername(String username) {
        return securityPersonnelRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Security Personnel not found"));
    }

    @Override
    @Transactional
    public SecurityIncident reportIncident(Long reporterId, Long locationId, 
                                           String eventType, String description, Long responseTeamId) {
        SecurityIncident incident = new SecurityIncident();
        incident.setEventType(eventType);
        incident.setDescription(description);
        incident.setLocation(String.valueOf(locationId));
        incident.setIncidentTimestamp(LocalDateTime.now());
        incident.setStatus(SecurityIncident.IncidentStatus.REPORTED);
        return securityIncidentRepository.save(incident);
    }

    @Override
    @Transactional
    public SecurityIncident respondToIncident(Long incidentId) {
        SecurityIncident incident = securityIncidentRepository.findById(incidentId)
            .orElseThrow(() -> new RuntimeException("Incident not found"));
        incident.setStatus(SecurityIncident.IncidentStatus.IN_PROGRESS);
        return securityIncidentRepository.save(incident);
    }

    @Override
    @Transactional
    public SecurityIncident resolveIncident(Long incidentId) {
        SecurityIncident incident = securityIncidentRepository.findById(incidentId)
            .orElseThrow(() -> new RuntimeException("Incident not found"));
        incident.setStatus(SecurityIncident.IncidentStatus.RESOLVED);
        return securityIncidentRepository.save(incident);
    }
}