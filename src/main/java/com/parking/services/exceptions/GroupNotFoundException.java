package com.parking.services.exceptions;

public class GroupNotFoundException extends RuntimeException {
    public GroupNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public GroupNotFoundException(String message) {
        super(message);
    }

    public GroupNotFoundException() {
    }
}
