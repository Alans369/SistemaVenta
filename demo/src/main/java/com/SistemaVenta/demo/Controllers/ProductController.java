package com.SistemaVenta.demo.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.SistemaVenta.demo.Model.Product;

@Controller
@RequestMapping("/admin")
public class ProductController {

    @GetMapping("/product/add")
    public String add(Product producto){
        return "producto";
    }

    @PostMapping("/product/add")
    public String add(){
        return "/";
    }


}
