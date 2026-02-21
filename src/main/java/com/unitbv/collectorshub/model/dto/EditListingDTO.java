package com.unitbv.collectorshub.model.dto;

import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EditListingDTO {
    @NonNull
    private Long productId;
    @NonNull
    private String link;
    @NonNull
    private String contact;
    @NonNull
    private Boolean isActive = true;
    @NonNull
    private BigDecimal price;
    @NonNull
    private String description;
}
