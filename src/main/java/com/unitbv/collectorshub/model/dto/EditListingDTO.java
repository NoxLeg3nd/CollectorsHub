package com.unitbv.collectorshub.model.dto;

import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EditListingDTO {
    @NonNull
    private Long newProductId;
    @NonNull
    private String newLink;
    @NonNull
    private String newContact;
    @NonNull
    private Boolean newIsActive = true;
    @NonNull
    private BigDecimal newPrice;
    @NonNull
    private String newDescription;
}
