package com.restful.service.cardsservice.model;

public class AuthResponse {

    private String email;
    private String accessToken;

    public AuthResponse() { }

    public AuthResponse(String email, String accessToken) {
        this.email = email;
        this.accessToken = accessToken;
    }
}
