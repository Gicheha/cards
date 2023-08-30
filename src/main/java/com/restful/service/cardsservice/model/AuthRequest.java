package com.restful.service.cardsservice.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Data
public class AuthRequest {

    @Getter
    @Setter
    @NotNull
    @Email
    @Length(min = 5, max = 50)
    private String email;

    @Getter
    @Setter
    @NotNull @Length(min = 5, max = 10)
    private String password;
}
