package com.SistemaVenta.demo.Controllers;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.*;;


@Controller
public class UserController {


    @RequestMapping("/register")
    public String register() {
        return "Registros/registrarse";
    }

   


}
