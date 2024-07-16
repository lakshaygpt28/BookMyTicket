package com.lakshaygpt28.bookmyticket.mapper;

import com.lakshaygpt28.bookmyticket.dto.TheatreDTO;
import com.lakshaygpt28.bookmyticket.model.Theatre;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TheatreMapper {

    TheatreMapper INSTANCE = Mappers.getMapper(TheatreMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    TheatreDTO toDto(Theatre theatre);
}
