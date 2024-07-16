package com.lakshaygpt28.bookmyticket.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequestDTO {
    @NotNull
    private Long bookingId;
    @NotNull
    private BigDecimal paymentAmount;
}
