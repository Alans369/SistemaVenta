package com.SistemaVenta.demo.Controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.SistemaVenta.demo.Model.Product;
import com.SistemaVenta.demo.Services.Implementation.ProductService;


@Controller
@RequestMapping("/user")
public class UserController {

  @Autowired
  private ProductService productService;

  @GetMapping("/dashboard")
  public String dashboard() {
    return "users/index";  
  }

  @GetMapping("/principal")
  public String principal(Model model) {
    List<Product> productos = productService.findAll();
   // model.addAttribute("productos", productos);
    return "vistacliente/paginaprincipal";  
  }

  


    
    
       
   

   


}