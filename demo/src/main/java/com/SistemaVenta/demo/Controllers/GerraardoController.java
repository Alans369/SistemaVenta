package com.SistemaVenta.demo.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.SistemaVenta.demo.Model.Brand;
import com.SistemaVenta.demo.Model.User;

@Controller
@RequestMapping("/gerardo")
public class GerraardoController {

  
    @GetMapping("/layout")
    public String layout() {
        return "layouts/_main_layout";
    }

    @GetMapping("/login")
    public String login(User user) {
        return "Registros/registrarse";
    }

    @GetMapping("/login1")
    public String iniciar(User user) {
        return "Registros/iniciar";
    }
    
    @GetMapping("/marcas")
    public String marcas(Brand marca) {
        return "Registros/marcas";
    }
}
