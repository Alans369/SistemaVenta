package com.SistemaVenta.demo.Controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.SistemaVenta.demo.Model.Product;
import com.SistemaVenta.demo.Services.Implementation.ProductService;
import com.SistemaVenta.demo.Utils.Util;

import jakarta.servlet.http.HttpServletRequest;




@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private ProductService productService;

    @GetMapping("/admin")
    public String admin1( Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size, HttpServletRequest request) {
        int currentPage = page.orElse(1)-1; // si no está seteado se asigna 0
        int pageSize = size.orElse(5); // tamaño de la página, se asigna 5
        Pageable pageable = PageRequest.of(currentPage, pageSize);
        String nombre = null;
        Integer categoriaId = null;
         Integer marcaId = null;
         String marca = Util.extractTokenFromCookie(request,"marca");
         System.out.println("Marca extraída de la cookie: " + marca);
         if (marca != null) {
             marcaId = Integer.parseInt(marca);
         }

       Page<Product> productos = productService.searchBynameOrCategory(nombre, categoriaId, marcaId, pageable);

       model.addAttribute("productos", productos);

        return "Registros/admin";

    }
}
