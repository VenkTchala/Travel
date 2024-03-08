package com.example.flightbookingservice.dto;

import lombok.*;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExchangeResponse {
    private Map<String,Long> conversion_rates;
}
