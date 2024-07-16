package com.lakshaygpt28.bookmyticket.mapper;

import com.lakshaygpt28.bookmyticket.dto.CityDTO;
import com.lakshaygpt28.bookmyticket.model.City;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CityMapper {

    CityMapper INSTANCE = Mappers.getMapper(CityMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    CityDTO toDto(City city);
}
