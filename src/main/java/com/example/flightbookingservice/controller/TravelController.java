package com.example.flightbookingservice.controller;

import com.example.flightbookingservice.dto.BookingData;
import com.example.flightbookingservice.dto.BookingResponse;
import com.example.flightbookingservice.dto.TravelFilters;
import com.example.flightbookingservice.dto.TravelOfferBookingDto;
import com.example.flightbookingservice.service.TravelService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/offers")
@RequiredArgsConstructor
public class TravelController {
    private final TravelService travelService;
    @PostMapping("/filter")
    public List<TravelOfferBookingDto> getFilteredBookings(@RequestBody TravelFilters filters){
        return  travelService.filterFlights(filters);
    }
    @PostMapping("/bookticket")
    public BookingResponse bookTicket(Authentication authentication, @RequestParam Long id, @RequestBody BookingData data){
        return travelService.bookTicket(authentication.getName(),data,id);
    }
    @GetMapping("/userbookings")
    public List<TravelOfferBookingDto> userBookings (Authentication authentication,@RequestParam String code){
        return travelService.getUserTravelBookings(authentication.getName(),code);
    }
}
