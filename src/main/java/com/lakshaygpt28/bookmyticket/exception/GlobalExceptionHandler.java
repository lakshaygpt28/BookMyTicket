package com.lakshaygpt28.bookmyticket.exception;

import com.lakshaygpt28.bookmyticket.dto.response.ApiResponse;
import com.lakshaygpt28.bookmyticket.dto.response.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        FieldError fieldError = ex.getBindingResult().getFieldError();
        String errorMessage = fieldError.getDefaultMessage();
        String fieldName = fieldError.getField();
        ErrorDTO errorDTO = new ErrorDTO(HttpStatus.BAD_REQUEST.value(), "Validation error for field: " + fieldName, errorMessage);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .success(false)
                .error(errorDTO)
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(ShowNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleShowNotFoundException(ShowNotFoundException ex) {
        ErrorDTO errorDTO = new ErrorDTO(HttpStatus.NOT_FOUND.value(), "Show not found", ex.getMessage());
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .success(false)
                .error(errorDTO)
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(BookingNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleBookingNotFoundException(BookingNotFoundException ex) {
        ErrorDTO errorDTO = new ErrorDTO(HttpStatus.NOT_FOUND.value(), "Booking not found", ex.getMessage());
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .success(false)
                .error(errorDTO)
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(TheatreNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleTheatreNotFoundException(TheatreNotFoundException ex) {
        ErrorDTO errorDTO = new ErrorDTO(HttpStatus.NOT_FOUND.value(), "Theatre not found", ex.getMessage());
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .success(false)
                .error(errorDTO)
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(SeatNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleSeatNotFoundException(SeatNotFoundException ex) {
        ErrorDTO errorDTO = new ErrorDTO(HttpStatus.NOT_FOUND.value(), "Seat not found", ex.getMessage());
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .success(false)
                .error(errorDTO)
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(PaymentProcessingException.class)
    public ResponseEntity<ApiResponse<Void>> handlePaymentProcessingException(PaymentProcessingException ex) {
        ErrorDTO errorDTO = new ErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error processing payment", ex.getMessage());
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .success(false)
                .error(errorDTO)
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
