package com.unitbv.collectorshub.repositories;

import com.unitbv.collectorshub.model.entities.Listing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {
    Optional<Listing> findById(Long id);
    Page<Listing> findAllByProduct_User_Id(Long userId, Pageable pageable);
}