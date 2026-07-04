package com.arx.bookratingapi.exceptionhandler.customexceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
