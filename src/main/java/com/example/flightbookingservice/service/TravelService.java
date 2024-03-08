package com.example.flightbookingservice.service;

import com.example.flightbookingservice.dto.*;
import com.example.flightbookingservice.entity.TravelOfferBooking;
import com.example.flightbookingservice.entity.TravelOfferListing;
import com.example.flightbookingservice.entity.TravelUser;
import com.example.flightbookingservice.entity.TravelUserDetails;
import com.example.flightbookingservice.mappers.TravelOfferBookingMapper;
import com.example.flightbookingservice.mappers.TravelOfferMapper;
import com.example.flightbookingservice.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@EnableScheduling
public class TravelService {
    private final TravelOfferBookingRepositoryCustom repositoryCustom;
    private final TravelOfferRepository travelOfferRepository;
    private final TravelOfferMapper travelOfferMapper;
    private final TravelOfferListingRepository travelOfferListingRepository;
    private final TravelOfferBookingMapper travelOfferBookingMapper;
    private final WebClient.Builder webclientBuilder;
    private final TravelUserRepository userRepository;
    private final TravelOfferBookingRepository travelOfferBookingRepository;
    private final TravelUserDetailsRepository travelUserDetailsRepository;

    private Map<String, Long> conversionRates;
    public void setConversionRates(Map<String, Long> conversionRates) {
        this.conversionRates = conversionRates;
    }
    public Map<String,Long> getConversionRates(){
        return conversionRates;
    }

    @Scheduled(initialDelay = 1000 * 20, fixedDelay=Long.MAX_VALUE)
    public void getUsdRatesOnce(){
        log.info("Conversion Rates Fetched");
        getUSDExchangeRate();
    }

    @Scheduled(cron = "0 0 0 * * *" , zone = "GMT")
    public void getUsdRatesDaily(){
        getUSDExchangeRate();
    }

    public void getUSDExchangeRate(){
                webclientBuilder.build()
                .get()
                .uri("https://v6.exchangerate-api.com/v6/fa576bf87afc0fd44c9a97dc/latest/USD")
                .retrieve()
                .bodyToMono(ExchangeResponse.class)
                .subscribe(i -> this.setConversionRates(i.getConversion_rates()));
    }

    public List<TravelOfferBookingDto> filterFlights(TravelFilters travelFilters){

        List<TravelOfferBookingDto> result = repositoryCustom
                .getoffersByFilters(travelFilters).stream()
                .map(travelOfferBookingMapper::toDto)
                .map(i ->{
                    i.getOffer().setPrice(
                        convertCurrency(i.getOffer().getPrice(),travelFilters.getCurrencyCode()));
                        return i;
                })
                .toList();
        return result;
    }

    private long convertCurrency(Long price , String code){
        Long factor = conversionRates.get(code);
        return price * factor;
    }

    public BookingResponse bookTicket(String email, BookingData data ,Long fligthId){


        try {
            TravelUser user = userRepository.getTravelUserByEmail(email)
                    .orElseThrow(IllegalArgumentException::new);


            TravelOfferListing travelOfferListing =
                    travelOfferListingRepository.findById(fligthId).
                            orElseThrow(IllegalArgumentException::new);

            if(travelOfferListing.getRemainingSeats() <= 0){
                return BookingResponse.builder()
                        .success(false)
                        .build();
            }

            travelOfferListing.setRemainingSeats(travelOfferListing.getRemainingSeats() - 1);

            travelOfferListingRepository.save(travelOfferListing);

            TravelOfferBooking travelOfferBooking =
                    travelOfferBookingRepository.getTravelOfferBookingByUser_Id(user.getId())
                            .orElse(createUserBooking(user));

            travelOfferBooking.getBooking().add(travelOfferListing);
            TravelUserDetails details =
            TravelUserDetails.builder()
                    .age(data.getAge())
                    .name(data.getName())
                    .gender(data.getGender())
                    .build();

            travelUserDetailsRepository.save(details);

            travelOfferBooking.getDetails().add(
                    details
            );

            travelOfferBookingRepository.save(travelOfferBooking);


            return BookingResponse.builder()
                    .success(true)
                    .build();
        }
        catch (Exception e){
            log.info(e.getClass().toString());
            return BookingResponse.builder()
                    .success(false)
                    .build();
        }
    }

    public List<TravelOfferBookingDto> getUserTravelBookings(String email,String code){
        try {
            TravelUser user = userRepository.getTravelUserByEmail(email).orElseThrow(IllegalArgumentException::new);
            return travelOfferBookingRepository.getTravelOfferBookingByUser_Id(user.getId())
                    .orElseThrow(IllegalArgumentException::new)
                    .getBooking()
                    .stream()
                    .map(travelOfferBookingMapper::toDto)
                    .map(i ->{
                        i.getOffer().setPrice(
                                convertCurrency(i.getOffer().getPrice(),code));
                        return i;
                    })
                    .toList();
        }
        catch (Exception e){
            return List.of();
        }
    }

    public TravelOfferBooking createUserBooking(TravelUser user){
        TravelOfferBooking tbooking = TravelOfferBooking.builder()
                .user(user)
                .booking(new ArrayList<>())
                .details(new ArrayList<>())
                .build();

        return travelOfferBookingRepository.save(tbooking);
    }
}
