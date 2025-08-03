package com.SistemaVenta.demo.Controllers;



import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller 
public class LoginController {

    private final AuthenticationManager authenticationManager;

	public LoginController(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}


    @PostMapping("/login1")
	public String login(@RequestParam String username, @RequestParam String password) {
        try {
        System.out.println("Attempting to authenticate user: " + username);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = this.authenticationManager.authenticate(token);

        // Si la autenticación es exitosa, Spring Security guarda el contexto.
        // Aquí puedes redirigir.
        System.out.println("User authenticated successfully");
        return "Registros/admin"; // Redirige a la página de admin

    } catch (AuthenticationException e) {
        // Esto se ejecuta si las credenciales son incorrectas
        System.out.println("Authentication failed: " + e.getMessage());
        return "index"; // Redirige a index con un parámetro de error
    }

        
		// ...
	}

	




}
