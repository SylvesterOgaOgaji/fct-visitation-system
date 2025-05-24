package com.fct.visitation.services.impl;

import com.fct.visitation.models.entity.*;
import com.fct.visitation.repositories.ResponseTeamRepository;
import com.fct.visitation.services.interfaces.EmailService;
import com.fct.visitation.services.interfaces.NotificationService;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

@Service
public class NotificationServiceImpl implements NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    
    @Autowired
    private EmailService emailService;
    
    @Autowired
    private ResponseTeamRepository responseTeamRepository;

    @Override
    public void notifyVisitor(Visitor visitor, String subject, String message) {
        try {
            String htmlContent = buildVisitorNotificationEmail(visitor, message);
            emailService.sendHtmlMessage(visitor.getEmail(), subject, htmlContent);
            logger.info("Visitor notification sent to: {}", visitor.getEmail());
        } catch (MessagingException e) {
            logger.error("Failed to send visitor notification", e);
        }
    }

    @Override
    public void sendAlertNotification(IncidentAlert alert) {
        try {
            if (alert.getVisitor() != null && alert.getVisitor().getEmail() != null) {
                Visitor visitor = alert.getVisitor();
                String subject = "Security Alert at " + getFacilityName(visitor);
                String message = String.format("A security alert has been reported regarding your visit at %s to meet %s. Please contact facility security for more information.",
                        formatDateTime(visitor.getAppointmentDateTime()),
                        getOfficerName(visitor));

                emailService.sendSimpleMessage(visitor.getEmail(), subject, message);
                logger.info("Alert notification sent for incident: {}", alert.getId());
            }
        } catch (Exception e) {
            logger.error("Failed to send alert notification", e);
        }
    }

    @Override
    public void sendEmergencyAlert(IncidentAlert alert) {
        try {
            if (alert.getVisitor() != null && alert.getVisitor().getEmail() != null) {
                Visitor visitor = alert.getVisitor();
                String subject = "EMERGENCY ALERT - " + getFacilityName(visitor);
                String message = String.format("An emergency situation has been reported at %s involving %s. This is an emergency notification.",
                        formatDateTime(visitor.getAppointmentDateTime()),
                        getOfficerName(visitor));

                emailService.sendSimpleMessage(visitor.getEmail(), subject, message);
                logger.info("Emergency alert sent for incident: {}", alert.getId());
            }
            notifyResponseTeam(alert);
        } catch (Exception e) {
            logger.error("Failed to send emergency alert", e);
        }
    }

    @Override
    public void notifyResponseTeam(IncidentAlert alert) {
        try {
            if (alert.getResponseTeam() != null) {
                ResponseTeam team = alert.getResponseTeam();
                String subject = "Security Incident Response Required - " + alert.getLocation();
                String message = buildResponseTeamMessage(alert);
                
                emailService.sendSimpleMessage(team.getContactEmail(), subject, message);
                logger.info("Response team notification sent to team: {}", team.getId());
            } else {
                logger.warn("No response team assigned to alert: {}", alert.getId());
            }
        } catch (Exception e) {
            logger.error("Failed to notify response team", e);
        }
    }

    @Override
    public void sendAlertStatusUpdateNotification(IncidentAlert alert) {
        try {
            String subject = "Alert Status Update - " + alert.getTitle();
            String message = String.format("Alert #%d has been updated to status: %s",
                    alert.getId(),
                    alert.getStatus().name());

            logger.info("Alert status update: {}", message);

            if (alert.getStatus() == IncidentAlert.Status.RESOLVED && alert.getVisitor() != null) {
                String visitorSubject = "Security Alert Resolved";
                String visitorMessage = "The security alert regarding your visit has been resolved. You may proceed with your visit as scheduled.";
                emailService.sendSimpleMessage(alert.getVisitor().getEmail(), visitorSubject, visitorMessage);
            }
        } catch (Exception e) {
            logger.error("Failed to send alert status update notification", e);
        }
    }

    @Override
    public void sendTeamAssignmentNotification(IncidentAlert alert, ResponseTeam team) {
        try {
            String subject = "New Security Incident Assignment - " + alert.getLocation();
            String message = String.format("Your team has been assigned to respond to a security incident at %s. Please respond immediately.",
                    alert.getLocation());
            
            emailService.sendSimpleMessage(team.getContactEmail(), subject, message);
            logger.info("Team assignment notification sent to team: {}", team.getId());
        } catch (Exception e) {
            logger.error("Failed to send team assignment notification", e);
        }
    }

    private String buildVisitorNotificationEmail(Visitor visitor, String message) {
        return String.format("""
            <html>
                <body>
                    <h2>FCT Government Visitation System</h2>
                    <p>Dear %s,</p>
                    <p>%s</p>
                    <p>Your visit details:</p>
                    <ul>
                        <li>Facility: %s</li>
                        <li>Time: %s</li>
                        <li>Officer: %s</li>
                    </ul>
                    <p>Please keep your QR code ready for scanning upon arrival.</p>
                    <p>Thank you,<br>FCT Government Visitation System</p>
                </body>
            </html>""",
            visitor.getFirstName(),
            message,
            getFacilityName(visitor),
            formatDateTime(visitor.getAppointmentDateTime()),
            getOfficerName(visitor));
    }

    private String buildResponseTeamMessage(IncidentAlert alert) {
        return String.format("""
            SECURITY ALERT
            
            Location: %s
            Time Reported: %s
            Reported By: %s
            Description: %s
            
            Please respond immediately to this alert. Confirm receipt by replying to this message.""",
            alert.getLocation(),
            formatDateTime(alert.getCreatedAt()),
            alert.getReporter() != null ? alert.getReporter().getFullName() : "Unknown",
            alert.getDescription());
    }

    private String formatDateTime(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(DATE_FORMATTER) : "N/A";
    }

    private String getFacilityName(Visitor visitor) {
        return visitor.getFacility() != null ? visitor.getFacility().getName() : "Unknown Facility";
    }

    private String getOfficerName(Visitor visitor) {
        return visitor.getOfficer() != null ? visitor.getOfficer().getFullName() : "Unknown Officer";
    }
}