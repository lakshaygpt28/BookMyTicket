package com.lakshaygpt28.bookmyticket.mapper;

import com.lakshaygpt28.bookmyticket.dto.ScreenDTO;
import com.lakshaygpt28.bookmyticket.model.Screen;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ScreenMapper {

    ScreenMapper INSTANCE = Mappers.getMapper(ScreenMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    ScreenDTO toDto(Screen screen);
}
