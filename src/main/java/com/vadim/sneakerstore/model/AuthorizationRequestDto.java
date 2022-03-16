package com.vadim.sneakerstore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthorizationRequestDto {

    @Email
    private String email;

   // @NotBlank
    @Size(min = 4, max = 20)
    private String password;
}
