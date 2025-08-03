package com.SistemaVenta.demo.Security;

import java.io.IOException;
import java.security.Key;
import java.util.Arrays;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final Key key = JwtUtil.getSecretKey(); // Obtener la misma clave secreta del JwtUtil

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = null;
        System.out.println("JWT Authentication Filter is running...");

        // Leer el token de la cookie
        if (request.getCookies() != null) {
            token = Arrays.stream(request.getCookies())
                    .filter(c -> c.getName().equals("JWT_TOKEN"))
                    .map(Cookie::getValue)
                    .findFirst()
                    .orElse(null);
        }

        // Si hay token, validarlo
        if (token != null) {
            try {
                Jws<Claims> claimsJws = Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(token);

                String username = claimsJws.getBody().getSubject();
                String role = claimsJws.getBody().get("role", String.class);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    username, null, Collections.singleton(authority)
            );
            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

            } catch (Exception e) {
                System.out.println("Token inválido o expirado: " + e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }
}
