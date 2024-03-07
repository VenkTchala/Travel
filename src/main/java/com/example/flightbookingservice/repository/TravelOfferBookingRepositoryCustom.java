package com.example.flightbookingservice.repository;

import com.example.flightbookingservice.dto.TravelFilters;
import com.example.flightbookingservice.entity.TravelOfferListing;

import java.util.List;

//@Repository
public interface TravelOfferBookingRepositoryCustom {
    List<TravelOfferListing> getoffersByFilters(TravelFilters filters);
}
