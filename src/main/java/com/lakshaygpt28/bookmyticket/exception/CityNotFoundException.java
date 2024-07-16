package com.lakshaygpt28.bookmyticket.exception;

public class CityNotFoundException extends RuntimeException {
    public CityNotFoundException(String message) {
        super(message);
    }
}
