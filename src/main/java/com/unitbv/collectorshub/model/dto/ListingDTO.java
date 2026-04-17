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

    @Id
    private Long id;
    @NonNull
    private ProductDTO product;
    @NonNull
    private String link;
    @NonNull
    private String contact;
    @NonNull
    private Boolean isActive = true;
    @NonNull
    private BigDecimal price;
    private String description;
    @NonNull
    private Long userId;
    private String username;
}
