package com.unitbv.collectorshub.model.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EditUserDTO {
    @NonNull
    private String newEmail;
    @NonNull
    private String newUsername;
    @NonNull
    private String newPassword;
}
