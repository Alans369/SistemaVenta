package com.SistemaVenta.demo.Controllers;




import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.core.AuthenticationException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.SistemaVenta.demo.Model.Role;
import com.SistemaVenta.demo.Model.User;
import com.SistemaVenta.demo.Security.JwtUtil;
import com.SistemaVenta.demo.Services.Implementation.RolService;
import com.SistemaVenta.demo.Services.Implementation.UserServices;
import com.SistemaVenta.demo.Utils.AuthUtils;

import jakarta.servlet.http.Cookie;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller 
public class LoginController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private UserServices userServices;

    @Autowired
    private RolService roleService;

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
        System.out.println("buscando al usario para el login" + username);
            
        String token = AuthUtils.login(authenticationManager, username, password);
    

        Cookie cookie = new Cookie("JWT_TOKEN",token);
        cookie.setHttpOnly(true); 
        cookie.setPath("/"); 
        response.addCookie(cookie);
        return "redirect:/"; 

    } catch (AuthenticationException e) {
        System.out.println("Authentication failed: " + e.getMessage());
        
        return "Registros/iniciar";
    }
    
        
		// ...
	}

    @PostMapping("/save")
    public String save(@RequestParam("rol") Integer rol, @Valid User usuario, BindingResult result, Model model, RedirectAttributes attributes) {
        
        if (result.hasErrors()) {
            return "Registros/registrarse";
        }
        System.out.println("Rol recibido: " + rol);

        try {
            Role role = roleService.findById(rol);
            usuario.setRole(role);

            String password = passwordEncoder.encode(usuario.getPassword());
            usuario.setPassword(password);
            usuario = userServices.create(usuario);

            String tokens = JwtUtil.generateToken(usuario.getUsername(),"ROL_"+role.getNombre(), 1000 * 60 * 10);
             System.out.println("token de registro de usario creado exitosamente: " + tokens);

            return "redirect:/Registros/iniciar";

            
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "Error al registrar: " + e.getMessage());
            return "Registros/registrarse";
        }   
        

        
    }


   

   @GetMapping("/access-denied")
    public String accessDenied() {
        return "error";
    }
}
	




