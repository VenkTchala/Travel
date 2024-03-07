package com.example.flightbookingservice.controller;

import com.example.flightbookingservice.dto.TravelFilters;
import com.example.flightbookingservice.dto.TravelOfferBookingDto;
import com.example.flightbookingservice.dto.TravelOfferDto;
import com.example.flightbookingservice.service.TravelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/offers")
@RequiredArgsConstructor
public class TravelController {
    private final TravelService travelService;
    @GetMapping ("/")
    public List<TravelOfferDto> getTravelOffers(){
        return  travelService.getallOffers();
    }
    @GetMapping("/bookings")
    public List<TravelOfferBookingDto> getTravelBookings(){
        return travelService.getAllFlights();
    }

    @GetMapping("/filter")
    public List<TravelOfferBookingDto> getFilteredBookings(@RequestBody TravelFilters filters){
        return  travelService.filterFlights(filters);
    }

}
