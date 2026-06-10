package com.unitbv.collectorshub.repositories;

import com.unitbv.collectorshub.model.entities.Listing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {
    Optional<Listing> findById(Long id);
    Page<Listing> findAllByUser_Id(Long userId, Pageable pageable);
    Page<Listing> findAllByIsActiveTrue(Pageable pageable);
    long countByIsActiveTrue();
    List<Listing> findAllByProduct_Id(Long id);
    Optional<Listing> findFirstByProduct_Id(Long productId);

    @Query(value = "SELECT l.* FROM listing_table l " +
            "JOIN product_table p ON l.product_id = p.id " +
            "WHERE l.is_active = true AND (" +
            "  LOWER(p.name) % LOWER(:query) OR " +
            "  LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "  LOWER(p.category) % LOWER(:query) OR " +
            "  LOWER(p.category) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "  LOWER(p.collection) % LOWER(:query) OR " +
            "  LOWER(p.collection) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "  LOWER(p.description) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "  LOWER(l.description) LIKE LOWER(CONCAT('%', :query, '%'))" +
            ") " +
            "ORDER BY similarity(p.name, :query) DESC",
            countQuery = "SELECT count(*) FROM listing_table l " +
                    "JOIN product_table p ON l.product_id = p.id " +
                    "WHERE l.is_active = true AND (" +
                    "LOWER(p.name) % LOWER(:query) OR " +
                    "LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
                    "LOWER(l.description) LIKE LOWER(CONCAT('%', :query, '%')))",
            nativeQuery = true)
    Page<Listing> searchAll(@Param("query") String query, Pageable pageable);

    @Query(value = "SELECT l.* FROM listing_table l " +
            "JOIN product_table p ON l.product_id = p.id " +
            "WHERE l.is_active = true AND l.user_id != :userId " +
            "AND p.category IN :categories " +
            "ORDER BY RANDOM()",
            countQuery = "SELECT count(*) FROM listing_table l " +
                    "JOIN product_table p ON l.product_id = p.id " +
                    "WHERE l.is_active = true AND l.user_id != :userId " +
                    "AND p.category IN :categories",
            nativeQuery = true)
    Page<Listing> findRecommendedByCategories(
            @Param("userId") Long userId,
            @Param("categories") List<String> categories,
            Pageable pageable
    );
}