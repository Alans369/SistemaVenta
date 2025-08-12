package com.SistemaVenta.demo.Utils;

import java.util.Arrays;

import com.SistemaVenta.demo.Security.JwtUtil;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

public class Util {

    public static String extractTokenFromCookie(HttpServletRequest request, String cookieName) {
        if (request.getCookies() != null) {
            return Arrays.stream(request.getCookies())
                    .filter(c -> c.getName().equals(cookieName))
                    .map(Cookie::getValue)
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    public static String obtenerUser( String token){
        Claims claimsJws = JwtUtil.extractAllClaims(token);
        return claimsJws.getSubject()!= null ? claimsJws.getSubject() : null;

    }

    public static Cookie Crear_cokie(String name,Integer marca) {
        System.out.println("creando cookie de marca");
        Cookie cookie = new Cookie(name,String.valueOf(marca));
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24); // 1 día

        return cookie;
    }

}
