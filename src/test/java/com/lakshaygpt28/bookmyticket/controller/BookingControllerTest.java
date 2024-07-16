package com.lakshaygpt28.bookmyticket.controller;

import com.lakshaygpt28.bookmyticket.model.Booking;
import com.lakshaygpt28.bookmyticket.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class BookingControllerTest {

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private BookingController bookingController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookingController).build();
    }

    @Test
    void createBooking_ValidRequest_ShouldReturnCreated() throws Exception {

        when(bookingService.createBooking(any(Long.class), any(Long.class), any(List.class)))
                .thenReturn(new Booking());

        mockMvc.perform(post("/api/v1/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\": 1, \"showId\": 1, \"seatIds\": [1, 2, 3]}"))
                .andExpect(status().isCreated());
    }

    @Test
    void createBooking_InvalidRequest_ShouldReturnBadRequest() throws Exception {

        mockMvc.perform(post("/api/v1/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\": 1, \"showId\": 1}"))  // Missing seatIds
                .andExpect(status().isBadRequest());
    }

    @Test
    void getBookingById_whenValidId_shouldReturnBooking() throws Exception {
        Long bookingId = 1L;
        Booking booking = new Booking();
        booking.setId(bookingId);
        when(bookingService.getBookingById(bookingId)).thenReturn(booking);
        mockMvc.perform(get("/api/v1/bookings/{bookingId}", bookingId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.response.bookingId").value(bookingId));
    }
}