package com.example.flightbookingservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TravelOfferBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private TravelUser user;
    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "travel_offer_listing_id"),
            inverseJoinColumns = @JoinColumn(name = "travel_offer_booking_id")
    )
    private List<TravelOfferListing> booking;
}
