package com.unitbv.collectorshub.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "listing_table")
public class Listing {
    @Id
    @GeneratedValue
    private Long id;
    private Long productId;
    private String link;
    private String contact;
    private Boolean isActive;
    private BigDecimal price;
    private String description;

}
