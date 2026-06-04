package com.unitbv.collectorshub.model.dto;

import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EditListingDTO {
    private Long newProductId;
    private String newLink;
    private String newContact;
    private Boolean newIsActive = true;
    private BigDecimal newPrice;
    private String newDescription;
}
