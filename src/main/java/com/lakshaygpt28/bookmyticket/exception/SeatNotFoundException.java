package com.lakshaygpt28.bookmyticket.exception;

public class SeatNotFoundException extends RuntimeException {
    public SeatNotFoundException(String message) {
        super(message);
    }
}
