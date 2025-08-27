package com.SistemaVenta.demo.Controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

import com.SistemaVenta.demo.Model.DetailsSale;
import com.SistemaVenta.demo.Model.Product;
import com.SistemaVenta.demo.Model.Sale;
import com.SistemaVenta.demo.Model.User;
import com.SistemaVenta.demo.Services.Implementation.CategoryService;
import com.SistemaVenta.demo.Services.Implementation.ProductService;
import com.SistemaVenta.demo.Services.Implementation.SaleService;
import com.SistemaVenta.demo.Utils.DtoProduct;
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

     @Autowired
    private SaleService Saleservice;

    @Autowired
    private CategoryService categoryService;

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
       model.addAttribute("pojo", new DtoProduct());

       for (Product producto : productos) {
           String imagenBase64 = producto.getImagenDataUri();
           producto.setImagenbase64(imagenBase64);
       }

        model.addAttribute("categories", categoryService.findAll());

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
       @GetMapping("/reporte/{visualizacion}")
       public ResponseEntity<byte[]> ReporteGeneral(@PathVariable("visualizacion") String visualizacion,@RequestParam("id") Integer id) {
        try {
            
          Sale venta = Saleservice.obtenerVentaPorId(id);

          User usuario=venta.getUsuario();

          System.out.println(usuario==null ? "el usaurio es nullo":"no es nulo");

         

          Map<String, Object> otrosDatos = new HashMap<>();
            otrosDatos.put("fecha", venta.getFecha());
            otrosDatos.put("total", venta.getTotal());
            otrosDatos.put("metodoPago", venta);
            otrosDatos.put("usuario", venta.getUsuario()==null?null:venta.getUsuario());
            otrosDatos.put("productos",venta.getDetallesVenta().size());
            

          int articulos=0;

          for (DetailsSale producto:venta.getDetallesVenta()){
              
            articulos+=producto.getCantidad();
          }
          otrosDatos.put("articulos",articulos);
            
          //  System.out.println("Productos encontrados: " + product);

          Map<String, Object> ventasData = new HashMap<>();
          ventasData.put("ventas", venta);

          Map<String, Object> productosData = new HashMap<>();
          productosData.put("detalles", venta.getDetallesVenta());




            // Genera el PDF. Si hay un error aquí, la excepción será capturada.
            byte[] pdfBytes = pdfGeneratorService.generatePdfFromHtml("reportes/product",ventasData,otrosDatos,productosData);

            HttpHeaders headers = new HttpHeaders();
          headers.setContentType(MediaType.APPLICATION_PDF);           
            // inline= vista previa, attachment=descarga el archivo
           headers.add("Content-Disposition", visualizacion+"; filename=reporte_general.pdf");

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);

        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    

  @GetMapping("/ventasproductos")
  public String ventasproductos(Model model,HttpServletRequest request,
  @RequestParam("size") Optional<Integer> size,
  @RequestParam("page") Optional<Integer> page) {

    Integer marcaId = null;
        String marca = Util.extractTokenFromCookie(request,"marca");
         System.out.println("Marca extraída de la cookie: " + marca);
         if (marca == null) {
          return "redirect:/admin/admin"; // Redirige al usuario a la página de login si no se encuentra la cookie
            
         }
          marcaId = Integer.parseInt(marca);
          ;

       int currentPage = page.orElse(1)-1; // si no está seteado se asigna 0
        int pageSize = size.orElse(1); // tamaño de la página, se asigna 5
        Pageable pageable = PageRequest.of(currentPage, pageSize);
        

      Page<Sale> ventas = Saleservice.obtenerTodos(marcaId, pageable);
      System.out.println("Ventas encontradas: " + ventas.toString());

      int totalPages = ventas.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

       model.addAttribute("ventas", ventas);

      return "ventasproductos/ventasproducos";  
  }
  }

