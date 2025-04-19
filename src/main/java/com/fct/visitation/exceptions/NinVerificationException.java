package com.fct.visitation.exceptions;

public class NinVerificationException extends RuntimeException {
    
    public NinVerificationException(String message) {
        super(message);
    }
    
    public NinVerificationException(String message, Throwable cause) {
        super(message, cause);
    }
}