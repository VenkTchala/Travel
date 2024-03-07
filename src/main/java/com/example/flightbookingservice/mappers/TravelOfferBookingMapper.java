package com.example.flightbookingservice.mappers;

import com.example.flightbookingservice.dto.TravelOfferBookingDto;
import com.example.flightbookingservice.entity.TravelOfferListing;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring" , uses = {TravelOfferMapper.class})
public interface TravelOfferBookingMapper {
    TravelOfferBookingDto toDto(TravelOfferListing travelOfferListing);
}
