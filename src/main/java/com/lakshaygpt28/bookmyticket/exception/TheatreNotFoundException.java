package com.lakshaygpt28.bookmyticket.exception;

public class TheatreNotFoundException extends RuntimeException {
    public TheatreNotFoundException(String message) {
        super(message);
    }
}
