package com.lakshaygpt28.bookmyticket.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentRequest {
    private Long bookingId;
    private BigDecimal paymentAmount;
}
