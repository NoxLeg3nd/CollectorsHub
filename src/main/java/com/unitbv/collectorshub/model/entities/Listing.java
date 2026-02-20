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
@Table(name = "listings_table")
public class Listing {
    @Id
    @GeneratedValue
    private Long id;
    private Long itemId;
    private String link;
    private Boolean isActive;
    private BigDecimal price;
    private String description;

}
