package com.example.flightbookingservice.repository;

import com.example.flightbookingservice.entity.TravelOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TravelOfferRepository extends JpaRepository<TravelOffer,Long> {
    List<TravelOffer> getTravelOffersByOriginCity(String city);
}
