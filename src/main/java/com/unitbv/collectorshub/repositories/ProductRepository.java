package com.unitbv.collectorshub.repositories;

import com.unitbv.collectorshub.model.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
    @Query(value = "SELECT p.* FROM product_table p " +
            "WHERE p.user_id = :userId AND (" +
            "  LOWER(p.name) % LOWER(:query) OR " +
            "  LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "  LOWER(p.category) % LOWER(:query) OR " +
            "  LOWER(p.category) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "  LOWER(p.collection) % LOWER(:query) OR " +
            "  LOWER(p.collection) LIKE LOWER(CONCAT('%', :query, '%'))" +
            ") " +
            "ORDER BY similarity(p.name, :query) DESC",
            countQuery = "SELECT count(*) FROM product_table p " +
                    "WHERE p.user_id = :userId AND (" +
                    "LOWER(p.name) % LOWER(:query) OR " +
                    "LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%')))",
            nativeQuery = true)
    Page<Product> searchByUserId(@Param("userId") Long userId,
                                 @Param("query") String query,
                                 Pageable pageable);
}