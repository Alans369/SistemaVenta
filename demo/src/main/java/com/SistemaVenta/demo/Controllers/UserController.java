package com.SistemaVenta.demo.Controllers;



import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.SistemaVenta.demo.Model.User;

import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;;


@Controller
public class UserController {


    @RequestMapping("/register")
    public String register() {
        return "Registros/registrarse";
    }

    @PostMapping("/save")
    public String save(@RequestParam(value = "rol", defaultValue = "1") Integer rol, User usuario, BindingResult result, Model model, RedirectAttributes attributes) {
        
        System.out.println(usuario);
        System.out.println("Rol seleccionado: " + rol);

        return "/Registros";

        

        
    }
    
       
       

   


}
