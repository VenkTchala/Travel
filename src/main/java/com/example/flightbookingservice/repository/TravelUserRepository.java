package com.example.flightbookingservice.repository;

import com.example.flightbookingservice.entity.TravelUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TravelUserRepository extends JpaRepository<TravelUser,Long> {
    Optional<TravelUser> getTravelUserByEmail(String email);
}
