package com.unitbv.collectorshub.model.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class EditUserPasswordDTO {
    @NonNull
    private String currentPassword;
    @NonNull
    private String newPassword;
}
