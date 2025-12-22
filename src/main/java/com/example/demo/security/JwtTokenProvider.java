package com.example.demo.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import com.example.demo.model.UserAccount;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private final String secret;
    private final long validityInMs;

    public JwtTokenProvider() {
        this.secret = "my-secret-key-for-jwt-token-generation-please-change-in-production-environment";
        this.validityInMs = 3600000; // 1 hour
    }

    public JwtTokenProvider(String secret, long validityInMs) {
        this.secret = secret;
        this.validityInMs = validityInMs;
    }

    public String generateToken(UserAccount user) {
    Date now = new Date();
    Date validity = new Date(now.getTime() + validityInMs);

    return Jwts.builder()
            .subject(user.getEmail())
            .claim("userId", user.getId())
            .claim("email", user.getEmail())
            .claim("role", user.getRole())
            .issuedAt(now)
            .expiration(validity)
            .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
            .compact();
}


    private Claims parseClaims(String token) {
    return Jwts.parser() // jjwt 0.12.x
            .verifyWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
            .build()
            .parseSignedClaims(token)
            .getPayload();
}

    public boolean validateToken(String token) {
        try {
            parseClaims(token);   
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            return false;
        }
    }

    public String getUsername(String token) {
        return parseClaims(token).getSubject();
    }

    public String getRole(String token) {
        return parseClaims(token).get("role", String.class);
    }
}
