package com.lakshaygpt28.bookmyticket.mapper;

import com.lakshaygpt28.bookmyticket.dto.ShowDTO;
import com.lakshaygpt28.bookmyticket.model.Show;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ShowMapper {
    ShowMapper INSTANCE = Mappers.getMapper(ShowMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "ticketPrice", target = "ticketPrice")
    @Mapping(source = "startTime", target = "startTime")
    @Mapping(source = "endTime", target = "endTime")
    @Mapping(source = "screen.id", target = "screenId")
    ShowDTO toDto(Show show);

}
