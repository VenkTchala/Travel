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
//        Join<Product, ProductType> productTypeJoin = productRoot.join(product_.getSingularAttribute("productType", ProductType.class), JoinType.INNER);
//
//        ListJoin<Product, Variant> varaintJoin = productRoot.join(product_.getList("variants", Variant.class), JoinType.LEFT);
//
//        ListJoin<Product, Tag> tagJoin = productRoot.join(product_.getList("tags", Tag.class), JoinType.LEFT);
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

//        if (!filters.getProductTypes().isEmpty()) {
//            predicates.add(
//                    cb.or(
//                            filters.getProductTypes()
//                                    .stream()
//                                    .map(i -> cb.like(productTypeJoin.get("name"), i))
//                                    .toArray(Predicate[]::new)
//                    )
//            );
//        }
//
//
//        if (!filters.getTags().isEmpty()) {
//
//            predicates.add(
//                    tagJoin.get("tagName").in(filters.getTags()));
//        }
//
        Predicate finalPredicate = cb.and(predicates.toArray(Predicate[]::new));
//
        cq.where(finalPredicate);
//
//        switch (filters.getSortBy()) {
//            case POPULARITY -> cq.orderBy(cb.desc(productRoot.get("ratingAverage")));
//            case PRICE_LOW_TO_HIGH -> cq.orderBy(cb.asc(varaintJoin.get("price")));
//            case PRICE_HIGH_TO_LOW -> cq.orderBy(cb.desc(varaintJoin.get("price")));
//        }
//
        TypedQuery<TravelOfferListing> query = entityManager.createQuery(cq);
//
        return query.getResultList();
    }
}

//@Service
//public class ProductDao implements ProductRepositoryCustom {
//    private final ProductMapper productMapper;
//    @PersistenceContext
//    EntityManager entityManager;
//
//    public ProductDao(ProductMapper productMapper) {
//        this.productMapper = productMapper;
//    }
//
//    @Override
//    public List<Product> getProductsOfBrandsByFilters(BrandFilters filters) {
//        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
//
//        Root<Product> productRoot = cq.from(Product.class);
//
//        Metamodel m = entityManager.getMetamodel();
//
//        EntityType<Product> product_ = m.entity(Product.class);
//
//        Join<Product, Brand> brandJoin = productRoot.join(product_.getSingularAttribute("brand", Brand.class));
//
//        Join<Product, ProductType> productTypeJoin = productRoot.join(product_.getSingularAttribute("productType", ProductType.class), JoinType.INNER);
//
//        ListJoin<Product, Variant> varaintJoin = productRoot.join(product_.getList("variants", Variant.class), JoinType.LEFT);
//
//        ListJoin<Product, Tag> tagJoin = productRoot.join(product_.getList("tags", Tag.class), JoinType.LEFT);
//
//        List<Predicate> predicates = new ArrayList<>(3);
//
//        Predicate brandPredicate = cb.equal(brandJoin.get("brandName"), filters.getBrand());
//
//        predicates.add(brandPredicate);
//
//        if (!filters.getProductTypes().isEmpty()) {
//            predicates.add(
//                    cb.or(
//                            filters.getProductTypes()
//                                    .stream()
//                                    .map(i -> cb.like(productTypeJoin.get("name"), i))
//                                    .toArray(Predicate[]::new)
//                    )
//            );
//        }
//
//
//        if (!filters.getTags().isEmpty()) {
//
//            predicates.add(
//                    tagJoin.get("tagName").in(filters.getTags()));
//        }
//
//        Predicate finalPredicate = cb.and(predicates.toArray(Predicate[]::new));
//
//        cq.where(finalPredicate);
//
//        switch (filters.getSortBy()) {
//            case POPULARITY -> cq.orderBy(cb.desc(productRoot.get("ratingAverage")));
//            case PRICE_LOW_TO_HIGH -> cq.orderBy(cb.asc(varaintJoin.get("price")));
//            case PRICE_HIGH_TO_LOW -> cq.orderBy(cb.desc(varaintJoin.get("price")));
//        }
//
//        TypedQuery<Product> query = entityManager.createQuery(cq);
//
//        return query.getResultList();
//    }
//
//    @Override
//    public List<Product> getProductsOfTypeByFilters(final ProductFilters filters) {
//        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
//
//        Root<Product> productRoot = cq.from(Product.class);
//
//        Metamodel m = entityManager.getMetamodel();
//
//        EntityType<Product> product_ = m.entity(Product.class);
////        EntityType<Brand> brand_ = m.entity(Brand.class);
////        EntityType<ProductType> productType_ = m.entity(ProductType.class);
//
//        Join<Product, ProductType> productTypeJoin = productRoot.join(product_.getSingularAttribute("productType", ProductType.class), JoinType.INNER);
//
//
//        Join<Product, Brand> brandJoin = productRoot.join(product_.getSingularAttribute("brand", Brand.class));
//
//        ListJoin<Product, Tag> tagJoin = productRoot.join(product_.getList("tags", Tag.class), JoinType.LEFT);
//
//        ListJoin<Product, Variant> varaintJoin = productRoot.join(product_.getList("variants", Variant.class), JoinType.INNER);
//
//        List<Predicate> finalPredicates = new ArrayList<>(4);
//
//        Predicate productTypePredicate = cb.equal(productTypeJoin.get("name"), filters.getType());
//
//        finalPredicates.add(productTypePredicate);
//
//        if (!filters.getBrands().isEmpty()) {
//            Predicate brandPredicate = cb.or(
//                    filters.getBrands()
//                            .stream()
//                            .map(i -> cb.like(brandJoin.get("brandName"), i))
//                            .toArray(Predicate[]::new)
//            );
//            finalPredicates.add(brandPredicate);
//        }
//
//        if (!filters.getTags().isEmpty()) {
//            finalPredicates.add(
//                    tagJoin.get("tagName").in(filters.getTags()));
//        }
//
//        Predicate finalPredicate = cb.and(finalPredicates.toArray(Predicate[]::new));
//
//        cq.select(productRoot).where(finalPredicate);
//
//        switch (filters.getSortBy()) {
//            case POPULARITY -> cq.orderBy(cb.desc(productRoot.get("ratingAverage")));
//            case PRICE_LOW_TO_HIGH -> cq.orderBy(cb.asc(varaintJoin.get("price")));
//            case PRICE_HIGH_TO_LOW -> cq.orderBy(cb.desc(varaintJoin.get("price")));
//        }
//
//        TypedQuery<Product> q = entityManager.createQuery(cq);
//        q.setFirstResult(0);
//        q.setMaxResults(50);
//        return q.getResultList();
//    }
//}

