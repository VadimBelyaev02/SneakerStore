package com.vadim.sneakerstore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordRequestDto {

    @Email
    private String email;

    @NotBlank
    private String oldPassword;

    @NotBlank
    private String newPassword;
}
