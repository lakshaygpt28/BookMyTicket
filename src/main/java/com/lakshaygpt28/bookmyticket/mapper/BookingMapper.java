package com.lakshaygpt28.bookmyticket.mapper;

import com.lakshaygpt28.bookmyticket.dto.response.BookingResponseDTO;
import com.lakshaygpt28.bookmyticket.model.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {ShowMapper.class, ShowSeatMapper.class})
public interface BookingMapper {

    BookingMapper INSTANCE = Mappers.getMapper(BookingMapper.class);

    @Mapping(source = "id", target = "bookingId")
    @Mapping(source = "bookingStatus", target = "bookingStatus")
    @Mapping(source = "totalAmount", target = "totalAmount")
    @Mapping(source = "show", target = "show")
    @Mapping(source = "showSeats", target = "showSeats")
    BookingResponseDTO toDto(Booking booking);
}
