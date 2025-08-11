package com.SistemaVenta.demo.Controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.SistemaVenta.demo.Model.Brand;
import com.SistemaVenta.demo.Model.User;
import com.SistemaVenta.demo.Services.Implementation.BrandService;
import com.SistemaVenta.demo.Services.Implementation.UserServices;
import com.SistemaVenta.demo.Utils.Util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;



@Controller
@RequestMapping("/admin")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @Autowired
    private UserServices userServices;

    @GetMapping("/marcas")
    public String marcas(Brand brand) {
         return "Registros/marcas";
    }

    @PostMapping("/marcaSave")
    public String save(@Valid Brand marca, BindingResult result, @RequestParam("imagenFile") MultipartFile imagenFile,
            Model model, RedirectAttributes attributes,HttpServletRequest request,HttpServletResponse response) {
        
        if (result.hasErrors()) {
            System.out.println("❌ Errores en la validación de la marca:");
            return "Registros/marcas";
        }
        System.out.println("📦 Marca recibida: " + marca);
        
        System.out.println("extraendo cookie");
        String token = Util.extractTokenFromCookie(request);

        String username = Util.obtenerUser(token);
        System.out.println("Usuario extraído del token buscandolo en base de datos: " + username);

        User user = userServices.findByUsername(username);

        if (user == null) {
            System.out.println("❌ Usuario no encontrado en la base de datos");
            attributes.addFlashAttribute("msg_error", "Usuario no encontrado");
            return "redirect:/admin/marcas";
        }
        System.out.println("Usuario encontrado en base de datos: " + user.toString());
        try {
            // Configurar el usuario (esto deberías obtenerlo de la sesión en un caso real)
         
            marca.setUser(user);
            // Procesar la imagen si se proporcionó una
            if (!imagenFile.isEmpty()) {
                marca.setImagen(imagenFile.getBytes());
            }
            // Guardar la marca
            Brand savedMarca = brandService.create(marca);
            attributes.addFlashAttribute("msg_success", "Marca guardada exitosamente con ID: " + savedMarca.getId());

            response.addCookie(Util.Crear_cokie("marca",savedMarca.getId()));

            return "redirect:/admin/admin";

        } catch (IOException e) {
            attributes.addFlashAttribute("msg_error", "Error al procesar la imagen: " + e.getMessage());
            return "redirect:/admin/marcas";
        } catch (Exception e) {
            attributes.addFlashAttribute("msg_error", "Error al guardar la marca: " + e.getMessage());
            return "redirect:/admin/marcas";
        }


        
    }

    
}
