package com.fct.visitation.services.impl;

import com.fct.visitation.models.entity.SecurityIncident;
import com.fct.visitation.models.entity.SecurityPersonnel;
import com.fct.visitation.models.entity.ResponseTeam;
import com.fct.visitation.models.entity.IncidentAlert;
import com.fct.visitation.services.interfaces.SecurityService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class SecurityServiceImpl implements SecurityService {

    @Override
    public SecurityIncident logSecurityEvent(String eventType, String description, String location) {
        return null;
    }

    @Override
    public SecurityIncident logSecurityEvent(String eventType, String description, Long locationId) {
        return null;
    }

    @Override
    public SecurityIncident logSecurityEvent(String eventType, String description) {
        return null;
    }

    @Override
    public List<SecurityIncident> findActiveIncidents() {
        return Collections.emptyList();
    }

    @Override
    public List<IncidentAlert> findActiveIncidentAlerts() {
        return Collections.emptyList();
    }

    @Override
    public List<ResponseTeam> findAllResponseTeams() {
        return Collections.emptyList();
    }

    @Override
    public List<SecurityPersonnel> findAllSecurityPersonnel() {
        return Collections.emptyList();
    }

    @Override
    public SecurityPersonnel findSecurityPersonnelById(Long id) {
        return null;
    }

    @Override
    public SecurityPersonnel findSecurityPersonnelByUsername(String username) {
        return null;
    }

    @Override
    public SecurityIncident reportIncident(Long reporterId, Long locationId, String eventType, String description, Long responseTeamId) {
        return null;
    }

    @Override
    public SecurityIncident respondToIncident(Long incidentId) {
        return null;
    }

    @Override
    public SecurityIncident resolveIncident(Long incidentId) {
        return null;
    }
}