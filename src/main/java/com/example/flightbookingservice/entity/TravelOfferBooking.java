package com.example.flightbookingservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
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

    @ManyToMany(cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    @JoinTable(
            joinColumns = @JoinColumn(name = "travel_user_Details_id"),
            inverseJoinColumns = @JoinColumn(name = "travel_offer_booking_id")
    )
    private List<TravelUserDetails> details;
}
