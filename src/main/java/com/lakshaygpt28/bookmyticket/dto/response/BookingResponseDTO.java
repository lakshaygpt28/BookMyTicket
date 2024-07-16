package com.lakshaygpt28.bookmyticket.dto.response;

import com.lakshaygpt28.bookmyticket.dto.ShowDTO;
import com.lakshaygpt28.bookmyticket.dto.ShowSeatDTO;
import com.lakshaygpt28.bookmyticket.model.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponseDTO {
    private Long bookingId;
    private BookingStatus bookingStatus;
    private ShowDTO show;
    private List<ShowSeatDTO> showSeats;
    private BigDecimal totalAmount;
}
