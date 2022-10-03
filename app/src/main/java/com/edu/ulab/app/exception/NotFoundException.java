package com.edu.ulab.app.exception;

public class NotFoundException extends RuntimeException {
    /**
     * Custom exception for handle exception in services
     * @param message
     */
    public NotFoundException(String message) {
        super(message);
    }
}
