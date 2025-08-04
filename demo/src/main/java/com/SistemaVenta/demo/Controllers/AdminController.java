package com.SistemaVenta.demo.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.SistemaVenta.demo.Model.Brand;


@Controller
@RequestMapping("/admin")
public class AdminController {

    
    @GetMapping("/marcas")
    public String marcas(Brand marca) {
        return "Registros/marcas";
    }

    @GetMapping("/admin")
    public String admin1( ) {
        return "Registros/admin";
    }
}
