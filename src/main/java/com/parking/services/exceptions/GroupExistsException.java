package com.parking.services.exceptions;

public class GroupExistsException extends RuntimeException {
    public GroupExistsException() {
    }

    public GroupExistsException(String message) {
        super(message);
    }

    public GroupExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public GroupExistsException(Throwable cause) {
        super(cause);
    }
}
