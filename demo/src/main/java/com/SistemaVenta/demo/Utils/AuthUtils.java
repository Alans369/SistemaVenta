package com.SistemaVenta.demo.Utils;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import com.SistemaVenta.demo.Security.JwtUtil;


public class AuthUtils {

    public static UserDetails auth(AuthenticationManager authenticationManager, String username, String password) throws AuthenticationException {
        System.out.println("buscando el usuario en la base de datos: " + username);
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(auth);
        return (UserDetails) authentication.getPrincipal();
    }


    public static String login(AuthenticationManager authenticationManager,String username, String role) throws AuthenticationException {
        UserDetails user = auth(authenticationManager, username, role);
        
        System.out.println("generando token para el login: " + user.getUsername());

        return JwtUtil.generateToken(user.getUsername(), user.getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .findFirst()
                .orElse("ROLE_USER"));
    }

   


    

}
