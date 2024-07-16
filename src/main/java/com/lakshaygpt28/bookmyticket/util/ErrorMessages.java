package com.lakshaygpt28.bookmyticket.util;

public class ErrorMessages {
    // Booking-related error messages
    public static final String BOOKING_NOT_FOUND = "Booking not found with id: %d";
    public static final String BOOKING_ALREADY_EXISTS = "Booking already exists with id: %d";
    public static final String BOOKING_CANCELLATION_UNAUTHORIZED = "User not authorized to cancel booking with id: %d";

    // Seat-related error messages
    public static final String SEAT_NOT_FOUND = "Seat not found with id: %d";
    public static final String INVALID_SEAT_SELECTION = "Invalid seat selection for seat id: %d";

    // Show-related error messages
    public static final String SHOW_NOT_FOUND = "Show not found with id: %d";

    // Theatre-related error messages
    public static final String THEATRE_NOT_FOUND = "Theatre not found with id: %d";

    // Payment-related error messages
    public static final String PAYMENT_PROCESSING_ERROR = "Error processing payment for booking id: %d";
    public static final String INSUFFICIENT_FUNDS = "Insufficient funds for user with id: %d";

    // Promo code-related error messages
    public static final String INVALID_PROMO_CODE = "Invalid promo code: %s";

    // General validation errors
    public static final String VALIDATION_ERROR = "Validation error on field: %s";

    public static final String CITY_NOT_FOUND = "City not found with id: %d";

    public static final String USER_NOT_FOUND = "User not found with id: %d";

    public static final String SCREEN_NOT_FOUND = "Screen not found with id: %d";

    public static final String BOOKING_NOT_AVAILABLE = "Booking not available with id: %d";
}
