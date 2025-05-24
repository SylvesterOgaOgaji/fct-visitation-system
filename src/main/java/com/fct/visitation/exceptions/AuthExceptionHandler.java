// File: src/main/java/com/fct/visitation/exceptions/AuthExceptionHandler.java
package com.fct.visitation.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(AuthExceptionHandler.class);

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(
            AuthenticationException ex,
            HttpServletRequest request) {
        
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        String errorCode = "AUTH-000";
        String message = "Authentication failed";

        // Handle specific authentication exceptions
        if (ex instanceof BadCredentialsException) {
            errorCode = "AUTH-001";
            message = "Invalid username or password";
        } else if (ex instanceof DisabledException) {
            errorCode = "AUTH-002";
            message = "Account is disabled";
        } else if (ex instanceof LockedException) {
            errorCode = "AUTH-003";
            message = "Account is locked";
        } else if (ex instanceof UsernameNotFoundException) {
            errorCode = "AUTH-004";
            message = "User not found";
        }

        logger.warn("Authentication failure: {} - {}", errorCode, message);
        
        return ResponseEntity.status(status)
                .body(new ErrorResponse(
                        status.value(),
                        "Authentication Error",
                        errorCode,
                        message,
                        request.getRequestURI()
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex,
            HttpServletRequest request) {
        
        logger.error("Unexpected error occurred: {}", ex.getMessage(), ex);
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Internal Server Error",
                        "GEN-001",
                        "An unexpected error occurred",
                        request.getRequestURI()
                ));
    }
}