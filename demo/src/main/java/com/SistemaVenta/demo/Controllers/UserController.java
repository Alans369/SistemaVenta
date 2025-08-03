package com.SistemaVenta.demo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;

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

import com.SistemaVenta.demo.Services.Implementation.UserServices;
import com.SistemaVenta.demo.Services.Implementation.RolService;

import jakarta.validation.Valid;


@Controller
public class UserController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private UserServices userServices;

    @Autowired
    private RolService roleService;




    @GetMapping("/register")
    public String register(User user) {
       
        return "Registros/registrarse";
    }

    @GetMapping("/login")
    public String login() {
       
        return "Registros/iniciar";
    }

    @GetMapping("/admin")
    public String admin() {
       
        return "Registros/admin";
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
            return "redirect:/Registros/iniciar";
            
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "Error al registrar: " + e.getMessage());
            return "Registros/registrarse";
        }   
        

        
    }
    
       
   

   


}