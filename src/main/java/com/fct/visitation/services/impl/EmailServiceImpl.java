package com.fct.visitation.services.impl;

import com.fct.visitation.services.interfaces.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Value("${spring.mail.username:noreply@fct.gov.ng}")
    private String fromEmail;

    private JavaMailSender emailSender;

    @Autowired(required = false)
    public EmailServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public void sendSimpleMessage(String to, String subject, String text) {
        try {
            if (emailSender == null) {
                logger.info("Email service not configured. Would send email to: {} with subject: {}", to, subject);
                return;
            }
            
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            emailSender.send(message);
            
            logger.info("Email sent to {} with subject: {}", to, subject);
        } catch (Exception e) {
            logger.error("Failed to send email to {}: {}", to, e.getMessage());
        }
    }

    @Override
    public void sendMessageWithAttachment(String to, String subject, String text, String attachmentPath) throws MessagingException {
        if (emailSender == null) {
            logger.info("Email service not configured. Would send email with attachment to: {} with subject: {}", to, subject);
            return;
        }
        
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        
        helper.setFrom(fromEmail);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text);
        
        FileSystemResource file = new FileSystemResource(new File(attachmentPath));
        helper.addAttachment(file.getFilename(), file);
        
        emailSender.send(message);
        logger.info("Email with attachment sent to {} with subject: {}", to, subject);
    }

    @Override
    public void sendHtmlMessage(String to, String subject, String htmlContent) throws MessagingException {
        if (emailSender == null) {
            logger.info("Email service not configured. Would send HTML email to: {} with subject: {}", to, subject);
            return;
        }
        
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        
        helper.setFrom(fromEmail);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);
        
        emailSender.send(message);
        logger.info("HTML email sent to {} with subject: {}", to, subject);
    }
}