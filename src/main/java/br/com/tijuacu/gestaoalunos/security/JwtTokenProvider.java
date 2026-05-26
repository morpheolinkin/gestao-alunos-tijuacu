package br.com.tijuacu.gestaoalunos.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final Key secretKey;
    private final long expirationInMillis;

    public JwtTokenProvider(
            @Value("${app.jwt.secret:uma-chave-secreta-bem-grande-e-dificil}") String secret,
            @Value("${app.jwt.expiration:3600000}") long expirationInMillis
    ) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
        this.expirationInMillis = expirationInMillis;
    }

    public String generateToken(String username, String role) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationInMillis);

        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        Claims claims = parseClaims(token).getPayload();
        return claims.getSubject();
    }

    public String getRoleFromToken(String token) {
        Claims claims = parseClaims(token).getPayload();
        Object role = claims.get("role");
        return role != null ? role.toString() : null;
    }

    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    private Jws<Claims> parseClaims(String token) {
        return Jwts.parser()              // na 0.12.6 é assim mesmo
                .verifyWith((SecretKey) secretKey)    // verifica assinatura com a chave
                .build()
                .parseSignedClaims(token);
    }
}