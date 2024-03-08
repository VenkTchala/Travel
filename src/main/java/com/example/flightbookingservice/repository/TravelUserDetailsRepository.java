package com.example.flightbookingservice.repository;

import com.example.flightbookingservice.entity.TravelUserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelUserDetailsRepository extends JpaRepository<TravelUserDetails , Long> {
}
