package com.parking.services.exceptions;

public class VehicleDoesNotExistException extends RuntimeException {
    public VehicleDoesNotExistException(Throwable cause) {
        super(cause);
    }

    public VehicleDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public VehicleDoesNotExistException(String message) {
        super(message);
    }

    public VehicleDoesNotExistException() {
    }
}
