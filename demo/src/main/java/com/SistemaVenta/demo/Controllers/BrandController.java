package com.SistemaVenta.demo.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.SistemaVenta.demo.Model.Brand;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class BrandController {


    @PostMapping("/marcaSave")
    public String createBrand(@Valid Brand brand,  @RequestParam("imagenFile") MultipartFile imagenFile, BindingResult result, Model model, RedirectAttributes attributes) {


        return "";
    }
}
