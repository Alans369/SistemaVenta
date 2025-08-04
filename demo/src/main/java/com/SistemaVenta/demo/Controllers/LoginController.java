package com.SistemaVenta.demo.Controllers;




import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.SistemaVenta.demo.Model.User;
import com.SistemaVenta.demo.Security.JwtUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller 
public class LoginController {

    private final AuthenticationManager authenticationManager;

	public LoginController(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

     @GetMapping("/layout")
    public String layout() {
        return "layouts/_main_layout";
    }

    @GetMapping("/register")
    public String login(User user) {
        return "Registros/registrarse";
    }

    @GetMapping("/login")
    public String iniciar() {
        return "Registros/iniciar";
    }


    @PostMapping("/login1")
	public String login(@RequestParam String username, @RequestParam String password, HttpServletResponse response) {
        try {
        System.out.println("Attempting to authenticate user: " + username);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = this.authenticationManager.authenticate(token);
        
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // ✅ Extraer el rol del usuario (toma el primer rol)
            String role = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("ROLE_USER"); // Rol por defecto
            
            System.out.println("User authenticated successfully: " + username);
            System.out.println("User role: " + role);
            System.out.println("All authorities: " + userDetails.getAuthorities());

        String tokens = JwtUtil.generateToken(token.getName(),role);
        System.out.println("Generated Token: " + tokens);


        Cookie cookie = new Cookie("JWT_TOKEN",tokens);
        cookie.setHttpOnly(true); // HttpOnly para que no sea accesible por JavaScript
        cookie.setPath("/"); // Disponible en toda la aplicación
        cookie.setMaxAge(60 * 60); // 1 hora de duración

        // Añadir la cookie a la respuesta
        response.addCookie(cookie);

        
        return "Registros/admin"; // Redirige a la página de admin

    } catch (AuthenticationException e) {
        // Esto se ejecuta si las credenciales son incorrectas
        System.out.println("Authentication failed: " + e.getMessage());
        
        return "Registros/iniciar"; // Redirige a index con un parámetro de error
    }
    
        
		// ...
	}


    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {
    // Crear una cookie vacía con el mismo nombre y maxAge = 0 para eliminarla
    Cookie cookie = new Cookie("JWT_TOKEN", null);
    cookie.setPath("/");
    cookie.setHttpOnly(true);
    cookie.setMaxAge(0); // Expira inmediatamente

    // Añadir la cookie a la respuesta (borrará la existente)
    response.addCookie(cookie);

    // Redirigir al login o a donde prefieras
    return "redirect:/Registros/iniciar";
}

 @GetMapping("/access-denied")
    public ResponseEntity<Map<String, Object>> accessDenied(
            @RequestParam(required = false) String url,
            HttpServletRequest request) {
        
        // Info del usuario actual
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        Map<String, Object> response = new HashMap<>();
        response.put("error", "403 Forbidden");
        response.put("message", "No tienes permisos para acceder a este recurso");
        response.put("attemptedUrl", url != null ? url : "unknown");
        response.put("user", auth != null ? auth.getName() : "anonymous");
        response.put("authorities", auth != null ? auth.getAuthorities() : "none");
        response.put("timestamp", LocalDateTime.now());
        
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

	




}
