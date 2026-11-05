package com.ticketingtool.auth.dto;

public class AuthResponse {

    private String message;
    private String token;
    private String tokenType;

    public AuthResponse() {
    }

    public AuthResponse(String message, String token, String tokenType) {
        this.message = message;
        this.token = token;
        this.tokenType = tokenType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
