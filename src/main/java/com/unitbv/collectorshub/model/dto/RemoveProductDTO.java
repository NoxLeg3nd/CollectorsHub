package com.unitbv.collectorshub.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RemoveProductDTO {
    @NonNull
    private String name;
    @NonNull
    private String collection;
    @NonNull
    private Integer manufactureYear;
}
