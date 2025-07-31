package com.SistemaVenta.demo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.SistemaVenta.demo.Model.Role;
import com.SistemaVenta.demo.Model.User;
import com.SistemaVenta.demo.Services.Implementation.UserServices;


@Controller
public class UserController {
    
    @Autowired
    private UserServices userServices;

    @GetMapping("/register")
    public String register(User user) {
       
        return "Registros/registrarse";
    }

    @PostMapping("/save")
    public String save(@RequestParam("rol") Integer rol, 
                      @ModelAttribute("user") User usuario, 
                      BindingResult result, 
                      Model model, 
                      RedirectAttributes attributes) {
        
        if (result.hasErrors()) {
            return "Registros/registrarse";
        }

        try {
            // Usar el servicio para crear el usuario con el rol especificado
            usuario = userServices.createWithRole(usuario, rol);
            attributes.addFlashAttribute("success", "Usuario registrado correctamente");
            return "redirect:/Registros/iniciar";
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "Error al registrar: " + e.getMessage());
            return "Registros/registrarse";
        }   
        

        
    }
    
       
       

   


}
