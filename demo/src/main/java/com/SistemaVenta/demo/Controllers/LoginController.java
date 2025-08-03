package com.SistemaVenta.demo.Controllers;

import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller 
public class LoginController {

    private final AuthenticationManager authenticationManager;

	public LoginController(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}


    @PostMapping("/login1")
	public ResponseEntity<Void> login(@RequestParam String username, @RequestParam String password) {
        System.out.println("Attempting to authenticate user: " + username);
        
        Authentication authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(username, password);
		Authentication authenticationResponse =this.authenticationManager.authenticate(authenticationRequest);

        if (authenticationResponse.isAuthenticated()) {
            System.out.println("User authenticated successfully: " + authenticationResponse.getName());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(401).build(); // Unauthorized
        }

        
		// ...
	}

	




}
