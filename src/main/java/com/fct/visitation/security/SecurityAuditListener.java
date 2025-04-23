package com.fct.visitation.security;

import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.listener.AuditApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.access.event.AuthorizationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class SecurityAuditListener {
    private static final Logger log = LoggerFactory.getLogger(SecurityAuditListener.class);
    
    @EventListener
    public void onAuditEvent(AuditApplicationEvent event) {
        AuditEvent auditEvent = event.getAuditEvent();
        log.info("Audit Event: Type={}, Principal={}, Data={}", 
            auditEvent.getType(), 
            auditEvent.getPrincipal(), 
            auditEvent.getData());
    }
    
    @EventListener
    public void onAuthenticationSuccess(AuthenticationSuccessEvent event) {
        log.info("Successful authentication: {}", event.getAuthentication().getName());
    }
    
    @EventListener
    public void onAuthorizationFailure(AuthorizationFailureEvent event) {
        log.warn("Authorization failure: {} for {}", 
            event.getAuthentication().getName(), 
            event.getAccessDeniedException().getMessage());
    }
}