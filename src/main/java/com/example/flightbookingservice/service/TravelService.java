package com.example.flightbookingservice.service;

import com.example.flightbookingservice.dto.TravelFilters;
import com.example.flightbookingservice.dto.TravelOfferBookingDto;
import com.example.flightbookingservice.dto.TravelOfferDto;
import com.example.flightbookingservice.mappers.TravelOfferBookingMapper;
import com.example.flightbookingservice.mappers.TravelOfferMapper;
import com.example.flightbookingservice.repository.TravelOfferBookingRepository;
import com.example.flightbookingservice.repository.TravelOfferBookingRepositoryCustom;
import com.example.flightbookingservice.repository.TravelOfferRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TravelService {
    private final TravelOfferBookingRepositoryCustom repositoryCustom;
    private final TravelOfferRepository travelOfferRepository;
    private final TravelOfferMapper travelOfferMapper;
    private final TravelOfferBookingRepository travelOfferBookingRepository;
    private final TravelOfferBookingMapper travelOfferBookingMapper;

    public List<TravelOfferDto> getallOffers(){
        return travelOfferRepository.findAll()
                .stream()
                .map(travelOfferMapper::toDto)
                .toList();
    }

    public List<TravelOfferBookingDto> getAllFlights(){
        return travelOfferBookingRepository
                .findAll().stream()
                .map(travelOfferBookingMapper::toDto)
                .toList();
    }

    public List<TravelOfferBookingDto> filterFlights(TravelFilters travelFilters){
        log.error(travelFilters.toString());

        List<TravelOfferBookingDto> result = repositoryCustom
                .getoffersByFilters(travelFilters).stream()
                .map(travelOfferBookingMapper::toDto)
                .toList();
        log.error(" " + result.size());
        return result;
    }
}
