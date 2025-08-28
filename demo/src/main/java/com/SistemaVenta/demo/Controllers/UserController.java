package com.SistemaVenta.demo.Controllers;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.SistemaVenta.demo.Model.DetailsSale;
import com.SistemaVenta.demo.Model.Product;
import com.SistemaVenta.demo.Model.Sale;
import com.SistemaVenta.demo.Model.User;
import com.SistemaVenta.demo.Services.Implementation.CategoryService;
import com.SistemaVenta.demo.Services.Implementation.ProductService;
import com.SistemaVenta.demo.Services.Implementation.SaleService;
import com.SistemaVenta.demo.Services.Implementation.UserServices;
import com.SistemaVenta.demo.Utils.Util;

import jakarta.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/user")
public class UserController {

  @Autowired
  private ProductService productService;

   @Autowired
    private CategoryService categoryService;


    @Autowired
    private SaleService service;

     @Autowired
    private UserServices userServices;

  @GetMapping("/dashboard")
  public String dashboard() {
    return "users/index";  
  }

  @GetMapping("/principal")
  public String principal( Model model, @RequestParam("page") Optional<Integer> page,
     @RequestParam("nombre") Optional<String> nombre,
     @RequestParam("categoria") Optional<Integer> categoriaId,
      @RequestParam("size") Optional<Integer> size,
       HttpServletRequest request) {

        int currentPage = page.orElse(1)-1; // si no está seteado se asigna 0
        int pageSize = size.orElse(6); // tamaño de la página, se asigna 5
        Pageable pageable = PageRequest.of(currentPage, pageSize);


     Page<Product> productos = productService.searchBynameOrCategory1(nombre.orElse(null), categoriaId.orElse(null), pageable);

     

     model.addAttribute("categories", categoryService.findAll());

    for (Product producto : productos) {
           String imagenBase64 = producto.getImagenDataUri();
           producto.setImagenbase64(imagenBase64);
       }

    int totalPages = productos.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

  
    model.addAttribute("productos", productos);
    return "vistacliente/paginaprincipal";  
  }

  @GetMapping("/pago")
  public String pago() {
    return "metodosdecompra/pago";
  }

   @GetMapping("/Historial")
  public String Historial( HttpServletRequest request,Model model, @RequestParam("page") Optional<Integer> page,@RequestParam("size") Optional<Integer> size) {

    String token = Util.extractTokenFromCookie(request,"JWT_TOKEN");

    String username = Util.obtenerUser(token);
    System.out.println("Usuario extraído del token buscandolo en base de datos: " + username);

    User user = userServices.findByUsername(username);

    int currentPage = page.orElse(1)-1; // si no está seteado se asigna 0
        int pageSize = size.orElse(3); // tamaño de la página, se asigna 5
        Pageable pageable = PageRequest.of(currentPage, pageSize);

    Page<Sale> ventas = service.obtenerVentasPorUsuario(user.getId(),pageable);



    for(Sale item : ventas){
      System.out.println("Venta ID: " + item.getId());

        

        for(DetailsSale detalle : item.getDetallesVenta()) {
           String imagenBase64 = detalle.getProducto().getImagenDataUri();
           detalle.getProducto().setImagenbase64(imagenBase64);
           detalle.getProducto().setImagen(null);
           detalle.getProducto().getMarca().setImagen(null);
       }

       
    }

    

       
       
      

        




    int totalPages = ventas.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

  
    model.addAttribute("compras", ventas);



    return "vistacliente/Historial";
  }    

  
   

   


}