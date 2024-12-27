package com.assignment.axievaassignment.exceptions;

public class GlobalException extends Exception {

    public GlobalException(String message) {
        super(message);
    }

    public GlobalException(String message, Throwable t) {
        super(message, t);
    }
}
