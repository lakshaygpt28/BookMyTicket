package com.lakshaygpt28.bookmyticket.mapper;

import com.lakshaygpt28.bookmyticket.dto.ShowSeatDTO;
import com.lakshaygpt28.bookmyticket.model.ShowSeat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ShowSeatMapper {
    ShowSeatMapper INSTANCE = Mappers.getMapper(ShowSeatMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "seat.seatNumber", target = "seatNumber")
    ShowSeatDTO toDto(ShowSeat showSeat);
}
