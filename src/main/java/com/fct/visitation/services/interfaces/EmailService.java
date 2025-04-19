package com.fct.visitation.services.interfaces;

import jakarta.mail.MessagingException;

public interface EmailService {
    void sendSimpleMessage(String to, String subject, String text);
    void sendMessageWithAttachment(String to, String subject, String text, String attachmentPath) throws MessagingException;
    void sendHtmlMessage(String to, String subject, String htmlContent) throws MessagingException;
}