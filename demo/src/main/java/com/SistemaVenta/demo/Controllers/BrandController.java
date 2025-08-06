package com.SistemaVenta.demo.Controllers;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.SistemaVenta.demo.Model.Brand;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class BrandController {


    @PostMapping("/marcaSave")
    public String createBrand(@Valid  Brand marca,
                           @RequestParam("imagenFile") MultipartFile imagenFile,
                           Model model, BindingResult result) {

            try {
                    if (!imagenFile.isEmpty()) {
                        byte[] bytes = imagenFile.getBytes();
                        marca.setImagen(bytes);
                    }

                    System.out.println("Marca creada: " + marca.getNombre());

                    
                    return "redirect:/admin/marcas";

                } catch (IOException e) {
                    model.addAttribute("error", "Error al subir la imagen");
                    return "error";
                }


        
    }

    
}
