package com.example.flightbookingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TravelOfferDto {
    private Long id;
    private String origin;
    private String originCity;
    private String destination;
    private String destinationCity;
    private String airline;
    private Long available;
    private String departureTime;
    private String arrivalTime;
    private String totalTime;
    private Long connections;
    private Long price;
}
