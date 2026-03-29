package com.unitbv.collectorshub.repositories;

import com.unitbv.collectorshub.model.entities.Favourites;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavouritesRepository extends JpaRepository<Favourites, Long> {
    Page<Favourites> findAllByUser_Id(Long userId, Pageable pageable);
}
