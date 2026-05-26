package br.com.tijuacu.gestaoalunos.dto.response;

public record AuthResponse(
        String token,
        String tokenType, // "Bearer"
        String username,
        String role
) {}