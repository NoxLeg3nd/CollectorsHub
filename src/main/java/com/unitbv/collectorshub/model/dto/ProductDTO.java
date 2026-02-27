package com.unitbv.collectorshub.model.dto;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    @Id
    private Long id;
    @NonNull
    private String name;
    @NonNull
    private String category;
    @NonNull
    private String collection;
    @NonNull
    private Integer manufactureYear;
    @Nullable
    private String image;
    @NonNull
    private String description;
    @NonNull
    private Long userId;

}
