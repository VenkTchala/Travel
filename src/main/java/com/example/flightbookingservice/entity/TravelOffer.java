package com.example.flightbookingservice.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TravelOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    private String connectionCity;
    private Long price;
}
