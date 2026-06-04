package com.unitbv.collectorshub.model.dto;

import jakarta.annotation.Nullable;
import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class EditProductDTO {
    private String newProductName;
    private String newProductDescription;
    private String newProductImage;
    private String newProductCollection;
    private String newProductCategory;
    private Integer newManufactureYear;
}
