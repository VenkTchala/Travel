package com.example.flightbookingservice;

import com.example.flightbookingservice.entity.TravelOffer;
import com.example.flightbookingservice.entity.TravelOfferListing;
import com.example.flightbookingservice.repository.TravelOfferBookingRepository;
import com.example.flightbookingservice.repository.TravelOfferRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;


@Component
@RequiredArgsConstructor
@Slf4j
public class Runner implements CommandLineRunner {
    private final TravelOfferRepository repository;
    private final TravelOfferBookingRepository travelOfferBookingRepository;

    @Override
    public void run(String... args) throws Exception {
        JSONArray jsonArray;

        JSONParser parser = new JSONParser();
        try {
            URL url = getClass().getResource("/Offers.json");
            jsonArray = (JSONArray) parser.parse(new FileReader(url.toURI().getPath()));

            log.error("Size :  " + jsonArray.size());

            for (Object o : jsonArray) {

                JSONObject offer = (JSONObject) o;
                String origin = (String) offer.get("origin");
                String originCity = (String) offer.get("origin_city");
                String destination = (String) offer.get("Destination");
                String destinationCity = (String) offer.get("Destination_city");
                String airline = (String) offer.get("airline");
                long available = (long) offer.get("available");
                long connections = (long) offer.get("connections");
                long price = (long) offer.get("connections");


                TravelOffer travelOffer = TravelOffer.builder()
                        .origin(origin)
                        .originCity(originCity)
                        .destination(destination)
                        .destinationCity(destinationCity)
                        .airline(airline)
                        .available(available)
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

                            travelOfferBookingRepository.save(travelOfferListing);
                        });

            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }



    }
}