package com.example.flightbookingservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TravelOfferListing {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Temporal(TemporalType.DATE)
    private LocalDate date;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private TravelOffer offer;
    private Long remainingSeats;
}
