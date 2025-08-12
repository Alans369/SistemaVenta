package com.SistemaVenta.demo.Controllers;



import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.SistemaVenta.demo.Model.Category;
import com.SistemaVenta.demo.Model.Product;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class ProductController {

    @GetMapping("/product/add")
    public String add(Product product){
        return "productos/product";
    }

    @PostMapping("/product/add")
    public String add(@Valid Product producto, BindingResult result, @RequestParam("imagenFile") MultipartFile imagenFile
    ,@RequestParam Integer categoria){



        // Validaciones personalizadas
        if (categoria==null || categoria < 0) {
            result.addError(new FieldError("product", "category", "debes de selecionar una categoria al que pertenesera el producto"));
        }

        if(!imagenFile.isEmpty() || imagenFile == null){ 
            result.addError(new FieldError("product", "imagen", "la imagen no puede estar vacia"));
        }


        if (result.hasErrors()) {
            return "productos/product";
        }

        System.out.println(categoria);

        Category category = new Category();
        category.setId(categoria);
      
        producto.setCategory(category);
        System.out.println(producto);

        return "redirect:/admin/admin";
    }




}
