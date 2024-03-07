package com.example.flightbookingservice.mappers;

import com.example.flightbookingservice.dto.TravelOfferDto;
import com.example.flightbookingservice.entity.TravelOffer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TravelOfferMapper {
    TravelOfferMapper MAPPER = Mappers.getMapper(TravelOfferMapper.class);
    TravelOfferDto toDto(TravelOffer travelOffer);
}
