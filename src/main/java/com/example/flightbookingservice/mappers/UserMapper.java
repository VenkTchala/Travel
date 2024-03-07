package com.example.flightbookingservice.mappers;

import com.example.flightbookingservice.dto.UserDto;
import com.example.flightbookingservice.entity.TravelUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper
{
    UserMapper MAPPER = (UserMapper) Mappers.getMapper((Class)UserMapper.class);

    UserDto convert(final TravelUser user);
}

