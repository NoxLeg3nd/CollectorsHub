package com.unitbv.collectorshub.model.dto;

import jakarta.annotation.Nullable;
import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class EditProductDTO {
    @NonNull
    private String newProductName;
    @NonNull
    private String newProductDescription;
    @Nullable
    private String newProductImage;
    @NonNull
    private String newProductCollection;
    @NonNull
    private String newProductCategory;
    @NonNull
    private Integer newManufactureYear;
}
