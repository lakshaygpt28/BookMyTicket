package com.lakshaygpt28.bookmyticket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowDTO {
    private Long id;
    private MovieDTO movie;
    private BigDecimal ticketPrice;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long screenId;
}
