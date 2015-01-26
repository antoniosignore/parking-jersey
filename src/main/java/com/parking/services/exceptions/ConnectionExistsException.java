package com.parking.services.exceptions;

public class ConnectionExistsException extends RuntimeException {
    public ConnectionExistsException() {
    }

    public ConnectionExistsException(String message) {
        super(message);
    }

    public ConnectionExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionExistsException(Throwable cause) {
        super(cause);
    }
}
