package com.fct.visitation.services.interfaces;

import com.fct.visitation.models.entity.IncidentAlert;
import com.fct.visitation.models.entity.ResponseTeam;
import com.fct.visitation.models.entity.Visitor;

public interface NotificationService {
    void notifyVisitor(Visitor visitor, String subject, String message);
    void sendAlertNotification(IncidentAlert alert);
    void sendEmergencyAlert(IncidentAlert alert);
    void notifyResponseTeam(IncidentAlert alert);
    void sendAlertStatusUpdateNotification(IncidentAlert alert);
    void sendTeamAssignmentNotification(IncidentAlert alert, ResponseTeam team);
}