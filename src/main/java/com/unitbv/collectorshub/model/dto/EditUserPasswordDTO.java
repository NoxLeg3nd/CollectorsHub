package com.unitbv.collectorshub.model.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class EditUserPasswordDTO {
    private String currentPassword;
    private String newPassword;
}
