package com.SistemaVenta.demo.Controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.SistemaVenta.demo.Model.Category;
import com.SistemaVenta.demo.Model.Product;
import com.SistemaVenta.demo.Services.Implementation.CategoryService;
import com.SistemaVenta.demo.Utils.Util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class ProductController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/product/add")
    public String add(Product product, Model model){
        model.addAttribute("categories", categoryService.findAll());
        return "productos/product";
    }

    @PostMapping("/product/add")
    public String add(@Valid Product producto, BindingResult result, @RequestParam("imagenFile") MultipartFile imagenFile
    ,@RequestParam Integer categoria, Model model,HttpServletRequest request){

        //buscando la marca del admin
        String marca = Util.extractTokenFromCookie(request,"marca");

        if (marca == null) {
            System.out.println("❌ Marca no encontrada en la cookie");
            return "redirect:/admin/marcas";
        }




        // Validaciones personalizadas
        if (categoria==null || categoria < 0) {
            result.addError(new FieldError("product", "category", "debes de selecionar una categoria al que pertenesera el producto"));
        }

        if(imagenFile.isEmpty()){ 
            System.out.println("error en la imagen esta vacia "); 
            result.addError(new FieldError("product", "imagen", "la imagen no puede estar vacia"));
        }

        if(producto.getPrecioCompra()<=0){
            result.addError(new FieldError("product", "precioCompra", "el precio de compra debe ser mayor a 0"));
        }

        if(producto.getPrecioVenta()<=0){
            result.addError(new FieldError("product", "precioVenta", "el precio de venta debe ser mayor a 0"));
        }

        if (result.hasErrors()) {
            System.out.println("Errores en el formulario");
            model.addAttribute("categories", categoryService.findAll());
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
