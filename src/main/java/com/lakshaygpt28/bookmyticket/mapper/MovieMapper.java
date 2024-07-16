package com.lakshaygpt28.bookmyticket.mapper;

import com.lakshaygpt28.bookmyticket.dto.MovieDTO;
import com.lakshaygpt28.bookmyticket.model.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MovieMapper {

    MovieMapper INSTANCE = Mappers.getMapper(MovieMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    MovieDTO toDto(Movie movie);
}
