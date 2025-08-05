package com.SistemaVenta.demo.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

import java.security.Key;
import java.util.Date;

public class JwtUtil {

    private static final Key key = Keys.hmacShaKeyFor("UnaClaveSecretaSuperLargaParaFirmarJWT123456".getBytes()); // Clave secreta (solo ejemplo)
    private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hora

    public static String generateToken(String username, String role) {
        return generateToken(username, role, EXPIRATION_TIME);
    }

    public static String generateToken(String username, String role, long expirationTimeMillis) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTimeMillis))
                .signWith(key)
                .compact();
    }

    public static Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public static String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public static String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    public static Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    public static boolean isTokenExpired(String token) {
        System.out.println("verificando si el token expior desde jwtutil: " + extractExpiration(token).before(new Date()));
        return extractExpiration(token).before(new Date());
    }

    public static boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
            return !isTokenExpired(token);
        } catch (SignatureException | MalformedJwtException e) {
            System.out.println("Invalid JWT signature: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.out.println("JWT token is expired: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("JWT token is empty: " + e.getMessage());
        }
        return false;
    }

    public static Key getSecretKey() {
        return key;
    }
}
