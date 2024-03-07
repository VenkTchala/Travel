package com.example.flightbookingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TravelOfferBookingDto {
    private Long id;
    private LocalDate date;
    private TravelOfferDto offer;
    private Long remainingSeats;
}
