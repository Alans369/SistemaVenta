package com.SistemaVenta.demo.Controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.SistemaVenta.demo.Model.Brand;
import com.SistemaVenta.demo.Model.User;
import com.SistemaVenta.demo.Services.Implementation.BrandService;



@Controller
@RequestMapping("/admin")
public class BrandController {

    @Autowired
    private BrandService brandService;


    @PostMapping("/marcaSave")
    public String createBrand(@RequestParam String nombre, @RequestParam String descripcion,@RequestParam("imagenFile") MultipartFile imagenFile) {
                 
            try {
                    User user = new User();
                    user.setId(3);

                    Brand marca = new Brand();
                    marca.setNombre(nombre);
                    marca.setUser(user);
                    marca.setDescripcion(descripcion);
                    if (!imagenFile.isEmpty()) {
                        marca.setImagen(imagenFile.getBytes());
                    }

                    Brand result = brandService.create(marca);

                    System.out.println(result.getId());
                    
                    return "index";

                } catch (IOException e) {
                    
                    return "error";
                }


        
    }

    
}
