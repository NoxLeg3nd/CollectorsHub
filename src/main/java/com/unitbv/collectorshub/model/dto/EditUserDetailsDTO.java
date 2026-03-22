package com.unitbv.collectorshub.model.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EditUserDetailsDTO {
    @NonNull
    private String newEmail;
    @NonNull
    private String newUsername;
}
