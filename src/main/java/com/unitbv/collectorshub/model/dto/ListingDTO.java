package com.unitbv.collectorshub.model.dto;

import jakarta.persistence.Id;
import lombok.*;
import java.math.BigDecimal;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ListingDTO {
    private Long id;
    private ProductDTO product;
    private String link;
    private String contact;
    private Boolean isActive = true;
    private BigDecimal price;
    private String description;
    private Long userId;
    private String username;
}
