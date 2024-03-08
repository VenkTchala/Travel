package com.example.flightbookingservice.repository;

import com.example.flightbookingservice.entity.TravelOfferBooking;
import com.example.flightbookingservice.entity.TravelUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TravelOfferBookingRepository extends JpaRepository<TravelOfferBooking,Long> {
    Optional<TravelOfferBooking> getTravelOfferBookingByUser_Id(Long id);
}
