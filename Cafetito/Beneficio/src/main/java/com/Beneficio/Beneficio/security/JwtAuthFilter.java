package com.Beneficio.Beneficio.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(token)
                        .getBody();

                String username = claims.getSubject();
                Object rolObj = claims.get("rol");

                String rolName = null;
                if (rolObj != null) {
                    if (rolObj instanceof Integer || rolObj instanceof Long) {
                        // Mapear ID numérico al nombre del rol
                        int rolId = ((Number) rolObj).intValue();
                        switch (rolId) {
                            case 1: rolName = "BENEFICIO"; break;
                            case 2: rolName = "AGRICULTOR"; break;
                            case 3: rolName = "PESOCABAL"; break;
                            default: rolName = null;
                        }
                    } else {
                        // Si viene como string
                        String s = rolObj.toString();
                        rolName = s.startsWith("ROLE_") ? s.substring(5) : s;
                    }
                }

                List<SimpleGrantedAuthority> authorities = (rolName != null)
                        ? List.of(new SimpleGrantedAuthority("ROLE_" + rolName))
                        : Collections.emptyList();

                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(username, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception e) {
                SecurityContextHolder.clearContext();
            }
        }
        filterChain.doFilter(request, response);
    }
}