package com.lakshaygpt28.bookmyticket.controller;

import com.lakshaygpt28.bookmyticket.dto.response.ApiResponse;
import com.lakshaygpt28.bookmyticket.dto.response.BookingResponseDTO;
import com.lakshaygpt28.bookmyticket.mapper.BookingMapper;
import com.lakshaygpt28.bookmyticket.model.Booking;
import com.lakshaygpt28.bookmyticket.dto.request.BookingRequestDTO;
import com.lakshaygpt28.bookmyticket.service.BookingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Booking Controller", description = "APIs for managing bookings")
public class BookingController {
    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }


    @PostMapping("/bookings")
    public ResponseEntity<ApiResponse<BookingResponseDTO>> createBooking(@RequestBody @Valid BookingRequestDTO bookingRequestDTO) {
        Long userId = bookingRequestDTO.getUserId();
        Long showId = bookingRequestDTO.getShowId();
        List<Long> seatIds = bookingRequestDTO.getSeatIds();
        Booking savedBooking = bookingService.createBooking(userId, showId, seatIds);
        BookingResponseDTO bookingResponseDTO = BookingMapper.INSTANCE.toDto(savedBooking);
        ApiResponse<BookingResponseDTO> response = ApiResponse.<BookingResponseDTO>builder()
                .success(true)
                .message("Booking created successfully")
                .response(bookingResponseDTO)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/bookings/{id}")
    public ResponseEntity<ApiResponse<BookingResponseDTO>> getBookingById(@PathVariable Long id) {
        Booking booking = bookingService.getBookingById(id);
        BookingResponseDTO bookingResponseDTO = BookingMapper.INSTANCE.toDto(booking);
        ApiResponse<BookingResponseDTO> response = ApiResponse.<BookingResponseDTO>builder()
                .success(true)
                .message("Booking retrieved successfully")
                .response(bookingResponseDTO)
                .build();
        return ResponseEntity.ok(response);
    }
}
