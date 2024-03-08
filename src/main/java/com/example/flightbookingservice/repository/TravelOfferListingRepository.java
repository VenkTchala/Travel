package com.example.flightbookingservice.repository;

import com.example.flightbookingservice.entity.TravelOfferListing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelOfferListingRepository extends JpaRepository<TravelOfferListing,Long> {
}
