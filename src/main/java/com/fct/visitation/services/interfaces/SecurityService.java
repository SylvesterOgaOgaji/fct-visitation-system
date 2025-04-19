package com.fct.visitation.services.interfaces;

import com.fct.visitation.models.entity.SecurityIncident;
import com.fct.visitation.models.entity.SecurityPersonnel;
import com.fct.visitation.models.entity.ResponseTeam;
import com.fct.visitation.models.entity.IncidentAlert;

import java.util.List;

public interface SecurityService {
    /**
     * Log a security event with complete details
     * @param eventType Type of security event
     * @param description Detailed description of the event
     * @param location Location of the event
     * @return The logged security incident
     */
    SecurityIncident logSecurityEvent(String eventType, String description, String location);
    
    /**
     * Log a security event with location converted from Long
     * @param eventType Type of security event
     * @param description Detailed description of the event
     * @param locationId Location ID
     * @return The logged security incident
     */
    SecurityIncident logSecurityEvent(String eventType, String description, Long locationId);

    /**
     * Log a security event with default location
     * @param eventType Type of security event
     * @param description Detailed description of the event
     * @return The logged security incident
     */
    SecurityIncident logSecurityEvent(String eventType, String description);

    /**
     * Find active security incidents
     * @return List of active security incidents
     */
    List<SecurityIncident> findActiveIncidents();

    /**
     * Find active incident alerts (converted from SecurityIncident)
     * @return List of active incident alerts
     */
    List<IncidentAlert> findActiveIncidentAlerts();

    /**
     * Find all response teams
     * @return List of response teams
     */
    List<ResponseTeam> findAllResponseTeams();

    /**
     * Find all security personnel
     * @return List of security personnel
     */
    List<SecurityPersonnel> findAllSecurityPersonnel();

    /**
     * Find security personnel by ID
     * @param id Personnel ID
     * @return Security personnel
     */
    SecurityPersonnel findSecurityPersonnelById(Long id);

    /**
     * Find security personnel by username
     * @param username Username to search for
     * @return Security personnel
     */
    SecurityPersonnel findSecurityPersonnelByUsername(String username);

    /**
     * Report a security incident
     * @param reporterId ID of reporting personnel
     * @param locationId Location ID
     * @param eventType Event type
     * @param description Event description
     * @param responseTeamId Response team ID
     * @return Created security incident
     */
    SecurityIncident reportIncident(Long reporterId, Long locationId, 
                                    String eventType, String description, Long responseTeamId);

    /**
     * Respond to an incident
     * @param incidentId Incident ID
     * @return Updated incident
     */
    SecurityIncident respondToIncident(Long incidentId);

    /**
     * Resolve an incident
     * @param incidentId Incident ID
     * @return Resolved incident
     */
    SecurityIncident resolveIncident(Long incidentId);
}