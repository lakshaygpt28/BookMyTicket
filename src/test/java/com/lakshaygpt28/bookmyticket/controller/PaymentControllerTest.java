package com.lakshaygpt28.bookmyticket.controller;

import com.lakshaygpt28.bookmyticket.request.PaymentRequest;
import com.lakshaygpt28.bookmyticket.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class PaymentControllerTest {

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private PaymentController paymentController;

    private MockMvc mockMvc;

    @Test
    void processPayment_ValidRequest_ShouldReturnOk() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(paymentController).build();

        when(paymentService.processPayment(any(PaymentRequest.class))).thenReturn(true);

        mockMvc.perform(post("/api/v1/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"bookingId\": 1, \"paymentAmount\": 100.00}"))
                .andExpect(status().isOk());
    }

    @Test
    void processPayment_InvalidRequest_ShouldReturnBadRequest() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(paymentController).build();

        mockMvc.perform(post("/api/v1/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"bookingId\": 1}"))  // Missing paymentAmount
                .andExpect(status().isBadRequest());
    }
}