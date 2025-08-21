package com.SistemaVenta.demo.Controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

import java.util.stream.IntStream;




@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private ProductService productService;

    @GetMapping("/admin")
    public String admin1( Model model, @RequestParam("page") Optional<Integer> page,
     @RequestParam("nombre") Optional<String> nombre,
     @RequestParam("categoria") Optional<Integer> categoriaId,
      @RequestParam("size") Optional<Integer> size,
       HttpServletRequest request) {
        int currentPage = page.orElse(1)-1; // si no está seteado se asigna 0
        int pageSize = size.orElse(5); // tamaño de la página, se asigna 5
        Pageable pageable = PageRequest.of(currentPage, pageSize);
        
        
        Integer marcaId = null;
        String marca = Util.extractTokenFromCookie(request,"marca");
         System.out.println("Marca extraída de la cookie: " + marca);
         if (marca != null) {
             marcaId = Integer.parseInt(marca);
         }

       Page<Product> productos = productService.searchBynameOrCategory(nombre.orElse(null), categoriaId.orElse(null), marcaId, pageable);

       model.addAttribute("productos", productos);
       
        int totalPages = productos.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        // model.addAttribute("nombre", nombre.orElse(null));


        return "Registros/admin";

    }
}
