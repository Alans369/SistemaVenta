package com.SistemaVenta.demo.Security;

import java.io.IOException;
import java.security.Key;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.SistemaVenta.demo.Services.Interfaces.IJwtValidationFilter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends OncePerRequestFilter implements IJwtValidationFilter {

    private final Key key = JwtUtil.getSecretKey();

   
    private static final List<String> PUBLIC_ROUTES = Arrays.asList(
        "/", "/home", "/login", "/login1", "/register", 
        "/static/**", "/templates/**", "/js", "/images", "/save",
        "/access-denied");


  
    private static final List<String> PROTECTED_ROUTES = Arrays.asList(
        "/admin", "/admin/admin","/user");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestPath = request.getRequestURI();
        String method = request.getMethod();
        String token = extractTokenFromCookie(request);
        
        System.out.println("🔍 Filtering: " + method + " " + requestPath);

        // ✅ 1. Si es ruta pública, permitir sin token
        if (isPublicRoute(requestPath)) {
            if(requestPath.equals("/login") ){
                System.out.println("✅ Ruta pública de login perotienes token: " + requestPath);
                if(token!=null){
                    System.out.println("reirigiendo seguntu rol");

                    Claims claimsJws = JwtUtil.extractAllClaims(token);
                    String role = claimsJws.get("role", String.class);

                    if(role.equals("ROLE_CLIENTE")){
                        response.sendRedirect("/user/dashboard");
                        return;
                    }
                    if(role.equals("ROLE_VENDEDOR")){
                        response.sendRedirect("/admin/admin");
                        return;
                    }


                }
                filterChain.doFilter(request, response);
                return;
            }
           System.out.println("✅ Ruta pública: " + requestPath);
                filterChain.doFilter(request, response);
                return;
        }

         if (isProtectedRoute(requestPath)) {
            System.out.println("🔒 Ruta protegida: " + requestPath);

            

            if (token == null) {
                // ❌ No hay token - redirigir a login
                System.out.println("❌ no hay token");
                handleNoTokenOrInvalidToken(request, response,"");
                return;
            }

            if (isNotExpired(token)) {
                System.out.println("❌ el token a expi");
                handleNoTokenOrInvalidToken(request, response,"login");
                return;
            }

            if (!isSignatureValid(token)) {
                System.out.println("❌ firma del token inválida");
                handleNoTokenOrInvalidToken(request, response,"");
                return;
            }

             try {
                // ✅ Validar token
                if (validateAndSetAuthentication(token, request)) {
                    System.out.println("✅ Token válido, acceso permitido");
                    filterChain.doFilter(request, response);
                    return;
                } else {
                    // ❌ Token inválido
                    System.out.println("❌ Token inválido, redirecting to login");
                    handleNoTokenOrInvalidToken(request, response,"login");
                    return;
                }
                
            } catch (Exception e) {
                // ❌ Token expirado o malformado
                System.out.println("❌ Token error: " + e.getMessage());
                //handleExpiredToken(request, response);
                return;
            }
         }

        /**String token = null;
      

      
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
        }*/

     //   filterChain.doFilter(request, response);
    }
    // Verifica si la ruta es pública
     private boolean isPublicRoute(String path) {
        return PUBLIC_ROUTES.stream().anyMatch(route -> 
            path.equals(route) || path.startsWith(route + "/")
        );
    }

    private boolean isProtectedRoute(String path) {
        return PROTECTED_ROUTES.stream().anyMatch(route -> 
            path.startsWith(route + "/") || path.equals(route)
        );
    }

    

    private void clearTokenCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("JWT_TOKEN", "");
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0); // Expira inmediatamente
        response.addCookie(cookie);
    }

    @Override
    public boolean validateAndSetAuthentication(String token, HttpServletRequest request) {
        try {
            Claims claimsJws = JwtUtil.extractAllClaims(token);

            String username = claimsJws.getSubject();
            String role = claimsJws.get("role", String.class);
            

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Asegurar prefijo ROLE_
                String roleWithPrefix = role.startsWith("ROLE_") ? role : "ROLE_" + role;
                
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(roleWithPrefix);
                
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        username, null, Collections.singleton(authority)
                );
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
                
                System.out.println("👤 Usuario autenticado: " + username + " con rol: " + roleWithPrefix);
                return true;
            }
            
        } catch (Exception e) {
            System.out.println("❌ Error validando token: " + e.getMessage());
            return false;
        }
        return false;
    }
    @Override
    public boolean isNotExpired(String token) {
        return JwtUtil.isTokenExpired(token);
    }
    @Override
    public boolean isSignatureValid(String token) {

        return JwtUtil.validateToken(token);
        
    }
   

    @Override
    public String extractTokenFromCookie(HttpServletRequest request) {
        if (request.getCookies() != null) {
            return Arrays.stream(request.getCookies())
                    .filter(c -> c.getName().equals("JWT_TOKEN"))
                    .map(Cookie::getValue)
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }
    @Override
    public void handleNoTokenOrInvalidToken(HttpServletRequest request, HttpServletResponse response,String type) throws IOException  {
        
        clearTokenCookie(response);

        if(type.equals("login")){

        }else{
        response.sendRedirect("/login?error=token-expired&from=" + request.getRequestURI());

        }
        
        // Limpiar cookie expirada
        
        
    }
    
    

    
}
