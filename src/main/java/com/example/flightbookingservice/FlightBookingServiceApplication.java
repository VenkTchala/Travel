package com.example.flightbookingservice;

import com.example.flightbookingservice.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties(value = {RsaKeyProperties.class})
@EnableScheduling
public class FlightBookingServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(FlightBookingServiceApplication.class, args);
    }
}
