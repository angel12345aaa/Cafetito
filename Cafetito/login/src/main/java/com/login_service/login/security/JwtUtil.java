package com.login_service.login.security;

import com.login_service.login.model.Usuario;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private static final String SECRET_KEY =
            "cafetitoSecretKey_SUPER_SEGURA_2026_con_mas_de_256_bits_123456789";

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(Usuario usuario) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("rol", usuario.getIdRol());
        claims.put("estado", usuario.getEstado());
        claims.put("idAgricultor", usuario.getIdAgricultor());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(usuario.getNombre())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public boolean isTokenValid(String token) {
        try {
            extractAllClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}