package com.example.flightbookingservice.dto;

import com.example.flightbookingservice.entity.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingData {
    private String name;
    private Gender gender;
    private byte age;
}

