package com.SistemaVenta.demo.Utils;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;


import com.SistemaVenta.demo.Model.User;
import com.SistemaVenta.demo.Security.JwtUtil;


public class AuthUtils {

    public static User auth(AuthenticationManager authenticationManager, String username, String password) throws AuthenticationException {
        System.out.println("buscando el usuario en la base de datos: " + username);
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(auth);
        return (User) authentication.getPrincipal();
    }


    public static String login(User user) throws AuthenticationException {

        System.out.println("generando token para el login: " + user.getUsername());

        return JwtUtil.generateToken(user.getUsername(), user.getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .findFirst()
                .orElse("ROLE_USER"));
    }

   


    

}
