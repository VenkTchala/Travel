package com.example.flightbookingservice.repository;

import com.example.flightbookingservice.dto.TravelFilters;
import com.example.flightbookingservice.entity.TravelOffer;
import com.example.flightbookingservice.entity.TravelOfferListing;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.Metamodel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class TravelOfferDao implements TravelOfferBookingRepositoryCustom {
    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public List<TravelOfferListing> getoffersByFilters(TravelFilters filters) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<TravelOfferListing> cq = cb.createQuery(TravelOfferListing.class);
//
        Root<TravelOfferListing> travelOfferBookingRoot = cq.from(TravelOfferListing.class);
//
        Metamodel m = entityManager.getMetamodel();
//
        EntityType<TravelOfferListing> tBooking_ = m.entity(TravelOfferListing.class);
//
        Join<TravelOfferListing, TravelOffer> travelJoin = travelOfferBookingRoot.join(tBooking_.getSingularAttribute("offer", TravelOffer.class));
//
//
        List<Predicate> predicates = new ArrayList<>(3);
//
        Predicate originPredicate = cb.equal(travelJoin.get("origin"), filters.getOrigin());
//
        predicates.add(originPredicate);

        Predicate destinationPredicate = cb.equal(travelJoin.get("destination"), filters.getDestination());

        predicates.add(destinationPredicate);

        Predicate datePredicate = cb.equal(travelOfferBookingRoot.get("date"),filters.getDate());

        predicates.add(datePredicate);

        Predicate notAvailablePredicate = cb.greaterThan(travelOfferBookingRoot.get("remainingSeats"),0);

        predicates.add(notAvailablePredicate);

//
        Predicate finalPredicate = cb.and(predicates.toArray(Predicate[]::new));
//
        cq.where(finalPredicate);
//
        TypedQuery<TravelOfferListing> query = entityManager.createQuery(cq);
        return query.getResultList();
    }
}


