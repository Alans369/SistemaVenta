package com.SistemaVenta.demo.Controllers;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.SistemaVenta.demo.Model.Product;

import com.SistemaVenta.demo.Model.Category;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class ProductController {

    @GetMapping("/product/add")
    public String add(Model model){
        model.addAttribute("producto", new Product());
        return "Test/add";
    }

    @PostMapping("/product/add")
    public String add(@Valid Product producto, BindingResult result, @RequestParam("imagen") MultipartFile imagenFile
    ,@RequestParam Integer categoria){

        // Validaciones personalizadas
        if (categoria==null || categoria < 0) {
            result.addError(new FieldError("producto", "category", "debes de selecionar una categoria al que pertenesera el producto"));
        }

        if(!imagenFile.isEmpty() || imagenFile == null){ 
            result.addError(new FieldError("producto", "imagen", "la imagen no puede estar vacia"));
        }


        if (result.hasErrors()) {
            return "Registros/registrarse";
        }

        System.out.println(categoria);

        Category category = new Category();
        category.setId(categoria);
      
        producto.setCategory(category);
        System.out.println(producto);

        return "hello";
    }




}
