package com.parking.services.exceptions;

public class ParkingDoesNotExistException extends RuntimeException {
    public ParkingDoesNotExistException(Throwable cause) {
        super(cause);
    }

    public ParkingDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParkingDoesNotExistException(String message) {
        super(message);
    }

    public ParkingDoesNotExistException() {
    }
}
