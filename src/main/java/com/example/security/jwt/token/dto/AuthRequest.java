package com.example.security.jwt.token.dto;

public record AuthRequest(
        String username,
        String password
) {
}
