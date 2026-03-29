package com.unitbv.collectorshub.repositories;

import com.unitbv.collectorshub.model.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findById(Long id);
    Optional<Product> findByName(String name);
    Optional<Product> findByCollection(String collection);
    Optional<Product> findByManufactureYear(Integer manufactureYear);
    Optional<Product> findByNameAndCollectionAndManufactureYear(String name, String collection, Integer manufactureYear);
    Optional<Product> findByCategory(String category);
    Optional<Product> findByUser_Id(Long userId);
    Page<Product> findAllByUser_Id(Long userId, Pageable pageable);
}