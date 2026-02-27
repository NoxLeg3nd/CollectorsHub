package com.unitbv.collectorshub.model.dto;

import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetUserDTO {
    @NonNull
    private String username;
    @NonNull
    private String email;
}
