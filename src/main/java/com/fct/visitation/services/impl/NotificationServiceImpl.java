package com.fct.visitation.services.impl;

import com.fct.visitation.models.entity.IncidentAlert;
import com.fct.visitation.models.entity.ResponseTeam;
import com.fct.visitation.models.entity.Visitor;
import com.fct.visitation.repositories.ResponseTeamRepository;
import com.fct.visitation.services.interfaces.EmailService;
import com.fct.visitation.services.interfaces.NotificationService;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);
    
    @Autowired
    private EmailService emailService;
    
    @Autowired
    private ResponseTeamRepository responseTeamRepository;
    
    @Override
    public void notifyVisitor(Visitor visitor, String subject, String message) {
        try {
            // Construct the email content
            String htmlContent = buildVisitorNotificationEmail(visitor, message);
            
            // Send the email
            emailService.sendHtmlMessage(visitor.getEmail(), subject, htmlContent);
            
            logger.info("Visitor notification sent to: {}", visitor.getEmail());
        } catch (MessagingException e) {
            logger.error("Failed to send visitor notification", e);
        }
    }
    
    @Override
    public void sendAlertNotification(IncidentAlert alert) {
        try {
            // Get the visitor
            Visitor visitor = alert.getVisitor();
            
            if (visitor != null) {
                String subject = "Security Alert at " + visitor.getFacility().getFacilityName();
                String message = "A security alert has been reported regarding your visit at " +
                        visitor.getAppointmentDatetime() + " to meet " +
                        visitor.getOfficer().getFullName() + ". Please contact facility security for more information.";
                
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
            // Get the visitor
            Visitor visitor = alert.getVisitor();
            
            if (visitor != null) {
                String subject = "EMERGENCY ALERT - " + visitor.getFacility().getFacilityName();
                String message = "An emergency situation has been reported at " +
                        visitor.getAppointmentDatetime() + " involving " +
                        visitor.getOfficer().getFullName() + ". This is an emergency notification.";
                
                emailService.sendSimpleMessage(visitor.getEmail(), subject, message);
                
                logger.info("Emergency alert sent for incident: {}", alert.getId());
            }
            
            // Also notify the response team
            notifyResponseTeam(alert);
        } catch (Exception e) {
            logger.error("Failed to send emergency alert", e);
        }
    }
    
    @Override
    public void notifyResponseTeam(IncidentAlert alert) {
        try {
            // Get the team from the repository using the team name stored in alert
            String teamName = alert.getResponseTeam();
            if (teamName != null && !teamName.isEmpty()) {
                // Assuming you have a method to find ResponseTeam by name
                ResponseTeam team = responseTeamRepository.findByTeamName(teamName);
                
                if (team != null) {
                    String subject = "Security Incident Response Required - " + alert.getLocation();
                    String message = buildResponseTeamMessage(alert);
                    
                    emailService.sendSimpleMessage(team.getEmail(), subject, message);
                    
                    // You could also implement SMS notifications here
                    // smsService.sendSms(team.getContactNumber(), shortMessage);
                    
                    logger.info("Response team notification sent to team: {}", team.getTeamId());
                } else {
                    logger.warn("Response team not found for name: {}", teamName);
                }
            }
        } catch (Exception e) {
            logger.error("Failed to notify response team", e);
        }
    }
    
    @Override
    public void sendAlertStatusUpdateNotification(IncidentAlert alert) {
        try {
            // Notify administrators about status change
            String subject = "Alert Status Update - " + alert.getTitle();
            String message = "Alert #" + alert.getId() + " has been updated to status: " + alert.getStatus();
            
            // You would typically have a list of admin emails to notify
            // For now we'll log it
            logger.info("Alert status update: {}", message);
            
            // If alert was resolved, notify the visitor
            if (alert.getStatus() == IncidentAlert.Status.RESOLVED && alert.getVisitor() != null) {
                String visitorSubject = "Security Alert Resolved";
                String visitorMessage = "The security alert regarding your visit has been resolved. " +
                        "You may proceed with your visit as scheduled.";
                
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
            String message = "Your team has been assigned to respond to a security incident at " +
                    alert.getLocation() + ". Please respond immediately.";
            
            emailService.sendSimpleMessage(team.getEmail(), subject, message);
            
            logger.info("Team assignment notification sent to team: {}", team.getTeamId());
        } catch (Exception e) {
            logger.error("Failed to send team assignment notification", e);
        }
    }
    
    // Helper methods to build email content
    private String buildVisitorNotificationEmail(Visitor visitor, String message) {
        StringBuilder builder = new StringBuilder();
        builder.append("<html><body>");
        builder.append("<h2>FCT Government Visitation System</h2>");
        builder.append("<p>Dear ").append(visitor.getFirstName()).append(",</p>");
        builder.append("<p>").append(message).append("</p>");
        builder.append("<p>Your visit details:</p>");
        builder.append("<ul>");
        builder.append("<li>Facility: ").append(visitor.getFacility().getFacilityName()).append("</li>");
        builder.append("<li>Time: ").append(visitor.getAppointmentDatetime()).append("</li>");
        builder.append("<li>Officer: ").append(visitor.getOfficer().getFullName()).append("</li>");
        builder.append("</ul>");
        builder.append("<p>Please keep your QR code ready for scanning upon arrival.</p>");
        builder.append("<p>Thank you,<br>FCT Government Visitation System</p>");
        builder.append("</body></html>");
        return builder.toString();
    }
    
    private String buildResponseTeamMessage(IncidentAlert alert) {
        StringBuilder builder = new StringBuilder();
        builder.append("SECURITY ALERT\n\n");
        builder.append("Location: ").append(alert.getLocation()).append("\n");
        builder.append("Time Reported: ").append(alert.getReportedAt()).append("\n");
        builder.append("Reported By: ").append(alert.getReportedBy() != null ? alert.getReportedBy() : "Unknown").append("\n");
        builder.append("Description: ").append(alert.getDescription()).append("\n\n");
        builder.append("Please respond immediately to this alert. Confirm receipt by replying to this message.");
        return builder.toString();
    }
}