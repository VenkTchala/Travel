package com.example.flightbookingservice;

import com.example.flightbookingservice.entity.TravelOffer;
import com.example.flightbookingservice.entity.TravelOfferListing;
import com.example.flightbookingservice.repository.TravelOfferListingRepository;
import com.example.flightbookingservice.repository.TravelOfferRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;


@Component
@RequiredArgsConstructor
@Slf4j
public class Runner implements CommandLineRunner {
    private final TravelOfferRepository repository;
    private final TravelOfferListingRepository travelOfferListingRepository;

    @Override
    public void run(String... args) throws Exception {
        JSONArray jsonArray;

        JSONParser parser = new JSONParser();
        try {
            URL url = getClass().getResource("/offers.json");
            InputStream resource = new ClassPathResource("offers.json").getInputStream();
            jsonArray = (JSONArray) parser.parse(new InputStreamReader(resource,"UTF-8"));


            for (Object o : jsonArray) {

                JSONObject offer = (JSONObject) o;
                String origin = (String) offer.get("origin");
                String originCity = (String) offer.get("origin_city");
                String destination = (String) offer.get("destination");
                String destinationCity = (String) offer.get("destination_city");
                String airline = (String) offer.get("airline");
                String departTime = (String) offer.get("departure_time");
                String arrivalTime = (String) offer.get("arrival_time");
                String totalTime = (String) offer.get("total_time");
                long available = (long) offer.get("available");
                long connections = (long) offer.get("connections");
                long price = (long) offer.get("price");


                TravelOffer travelOffer = TravelOffer.builder()
                        .origin(origin)
                        .originCity(originCity)
                        .destination(destination)
                        .destinationCity(destinationCity)
                        .airline(airline)
                        .available(available)
                        .arrivalTime(arrivalTime)
                        .totalTime(totalTime)
                        .departureTime(departTime)
                        .connections(connections)
                        .price(price)
                        .build();

               final TravelOffer travelOfferSaved = repository.save(travelOffer);

                LocalDate
                        .now()
                        .datesUntil(LocalDate.of(2024, Month.MARCH,31))
                        .forEach(i -> {
                            TravelOfferListing travelOfferListing = TravelOfferListing
                                    .builder()
                                    .date(i)
                                    .offer(travelOfferSaved)
                                    .remainingSeats(travelOfferSaved.getAvailable())
                                    .build();

                            travelOfferListingRepository.save(travelOfferListing);
                        });

            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }



    }
}