package com.unitbv.collectorshub.repositories;

import com.unitbv.collectorshub.model.entities.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {
    Optional<Listing> findById(Long id);
    Optional<Listing> findByProductId(Long productId);
    Optional<Listing> findByProductIdAndIsActive(Long productId, Boolean isActive);
    Optional<Listing> findByIsActive(Boolean isActive);
    Optional<Listing> findByLink(String link);

}
