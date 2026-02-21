package com.unitbv.collectorshub.repositories;

import com.unitbv.collectorshub.model.entities.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {
    Optional<Listing> findByListingId(Long ListingId);
    Optional<Listing> findByProductId(Long productId);
    Optional<Listing> findByIsActive(Boolean isActive);

}
