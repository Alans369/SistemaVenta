package com.SistemaVenta.demo.Controllers;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/gerardo")
public class GerraardoController {

  
    @GetMapping("/layout")
    public String layout() {
        return "layouts/_main_layout";
    }

    @GetMapping("/login")
    public String login() {
        return "Registros/registrarse";
    }

    

}
