package com.restful.service.cardsservice.model;

import lombok.Getter;
import lombok.Setter;

public class AuthResponse {

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String accessToken;

    public AuthResponse() {
    }

    public AuthResponse(String email, String accessToken) {
        this.email = email;
        this.accessToken = accessToken;
    }
}
