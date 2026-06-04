package com.unitbv.collectorshub.model.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EditUserDetailsDTO {
    private String newEmail;
    private String newUsername;
}
