package com.SistemaVenta.demo.Controllers;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

//import org.esfe.servicios.utilerias.PdfGeneratorService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.SistemaVenta.demo.Model.Product;
import com.SistemaVenta.demo.Services.Implementation.ProductService;
import com.SistemaVenta.demo.Utils.PdfGeneratorService;
import com.SistemaVenta.demo.Utils.Util;

import jakarta.servlet.http.HttpServletRequest;




@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private ProductService productService;

    @Autowired
    private PdfGeneratorService pdfGeneratorService;

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

     public static String convertByteListToBase64(List<byte> byteList) throws IOException {
        // Convertir la lista de bytes a un array de bytes
        byte[] byteArray = new byte[byteList.size()];
        for (int i = 0; i < byteList.size(); i++) {
            byteArray[i] = byteList.get(i);
        }
        // Codificar el array de bytes a Base64
        return Base64.getEncoder().encodeToString(byteArray);
    }


       @GetMapping("/reporte/{visualizacion}")
       public ResponseEntity<byte[]> ReporteGeneral(@PathVariable("visualizacion") String visualizacion) {
        try {
            List<Product> product = productService.findAll();
          //  System.out.println("Productos encontrados: " + product);

            for (Product p : product) {
                System.out.println("Producto: " + p.getNombre());

                String base64String = convertByteListToBase64(p.getImagen());
                System.out.println("Cadena Base64: " + base64String);
            }

            // Genera el PDF. Si hay un error aquí, la excepción será capturada.
            byte[] pdfBytes = pdfGeneratorService.generatePdfFromHtml("reportes/product", "productos", product);

            HttpHeaders headers = new HttpHeaders();
          headers.setContentType(MediaType.APPLICATION_PDF);           
            // inline= vista previa, attachment=descarga el archivo
           headers.add("Content-Disposition", visualizacion+"; filename=reporte_general.pdf");

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);

        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
