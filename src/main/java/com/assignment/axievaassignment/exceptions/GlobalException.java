package com.assignment.axievaassignment.exceptions;

/**
 * Global exception class to handle all exceptions in the application
 */
public class GlobalException extends Exception {

    public GlobalException(String message) {
        super(message);
    }

    public GlobalException(String message, Throwable t) {
        super(message, t);
    }
}
