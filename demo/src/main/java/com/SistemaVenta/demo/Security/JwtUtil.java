package com.SistemaVenta.demo.Security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtil {

    private static final Key key = Keys.hmacShaKeyFor("UnaClaveSecretaSuperLargaParaFirmarJWT123456".getBytes()); // Clave secreta (solo ejemplo)

    public static String generateToken(String username,String role) {
        long expirationTimeMillis = 1000 * 60 * 60; // 1 hora

        return Jwts.builder()
                .setSubject(username)
                 .claim("role", role) // Añadir el rol al token
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTimeMillis))
                .signWith(key)
                .compact();
    }

     public static Key getSecretKey() {
        return key;
    }
}
