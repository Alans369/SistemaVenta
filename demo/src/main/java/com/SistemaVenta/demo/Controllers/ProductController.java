package com.SistemaVenta.demo.Controllers;



import java.io.IOException;
import java.util.Optional;

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

import com.SistemaVenta.demo.Model.Brand;
import com.SistemaVenta.demo.Model.Category;
import com.SistemaVenta.demo.Model.Product;
import com.SistemaVenta.demo.Model.User;
import com.SistemaVenta.demo.Services.Implementation.BrandService;
import com.SistemaVenta.demo.Services.Implementation.CategoryService;
import com.SistemaVenta.demo.Services.Implementation.ProductService;
import com.SistemaVenta.demo.Services.Implementation.UserServices;
import com.SistemaVenta.demo.Utils.DtoProduct;
import com.SistemaVenta.demo.Utils.Util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;


@Controller
@RequestMapping("/admin")
public class ProductController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserServices userServices;

    @Autowired
    private BrandService brandService;

    @Autowired
    private ProductService productService;

    @GetMapping("/product/add")
    public String add(Product product, Model model){
        model.addAttribute("categories", categoryService.findAll());
        return "productos/product";
    }

    @PostMapping("/product/add")
    public String add(@Valid Product producto, BindingResult result, @RequestParam("imagenFile") MultipartFile imagenFile
    ,@RequestParam Integer categoria, Model model,HttpServletRequest request,HttpServletResponse response){

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
    

         //buscando la marca del admin
        String marca = Util.extractTokenFromCookie(request,"marca");
        System.out.println("Marca extraída de la cookie: " + marca);

        if (marca == null) {
            System.out.println("❌ Marca no encontrada en la cookie");
            String token = Util.extractTokenFromCookie(request,"JWT_TOKEN");
            String username = Util.obtenerUser(token);
            User user = userServices.findByUsername(username);
            Brand brand = brandService.findByUserId(user.getId());
            if (brand == null) {
                 return "redirect:/admin/marcas";
            }
            Cookie cookie = Util.Crear_cokie("marca",brand.getId());
            producto.setMarca(brand);
            response.addCookie(cookie);
        }

        
        Brand brand = new Brand();
        brand.setId(Integer.parseInt(marca));

        try {
            if (!imagenFile.isEmpty()) {
                producto.setImagen(imagenFile.getBytes());
            }
        } catch (IOException e) {
           return "redirect:/admin/product/add";
        }

        Category category = new Category();
        category.setId(categoria);
        producto.setMarca(brand);   
        producto.setCategory(category);
      



      try {
          productService.createOrEdit(producto);
          return "redirect:/admin/admin";
      } catch (Exception e) {
          System.out.println("Error al guardar el producto: " + e.getMessage());
          return "redirect:/admin/product/add";
      }

        
    }
    @GetMapping("/product/delete")
    public String getMethodName(@RequestParam("id") Optional<Integer> id ) {
        System.out.println(id);


       Boolean result = productService.delet(id.orElse(null));

       if(result){
        System.out.println("se aborrado el producto correctamente" + result);
        return "redirect:/admin/admin";
       }

       

       return "redirect:/admin/admin";
    }

    @PostMapping("/product/update")
    public String editar(DtoProduct dtoProduct,Model model,@RequestParam("imagenFile") MultipartFile imagenFile){
        System.out.println("llego al controlador"+ dtoProduct);

        Product producto = productService.selectById(dtoProduct.getId());
                producto.setNombre((dtoProduct.getNombre())!=null? dtoProduct.getNombre():producto.getNombre());
                producto.setPrecioCompra((dtoProduct.getPrecioCompra())!=0.0? dtoProduct.getPrecioCompra():producto.getPrecioCompra());
                producto.setPrecioVenta((dtoProduct.getPrecioVenta()) !=0.0? dtoProduct.getPrecioVenta():producto.getPrecioVenta());
                producto.setStock((dtoProduct.getStock() )!=null? dtoProduct.getStock():producto.getStock());

           try {
            if (!imagenFile.isEmpty()) {
                producto.setImagen(imagenFile.getBytes());
            }
            } catch (IOException e) {
              return "redirect:/admin/admin";
            }

        productService.createOrEdit(producto);

            

            return "redirect:/admin/admin";
        
        }
   
    




}
