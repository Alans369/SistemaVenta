package com.SistemaVenta.demo.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.SistemaVenta.demo.Model.DetailsSale;
import com.SistemaVenta.demo.Model.Product;
import com.SistemaVenta.demo.Model.Sale;

@Controller
@RequestMapping("/user")
public class SaleController {


    @PostMapping("/sale/create")
    @ResponseBody
    public ResponseEntity<Void> recibirProductosAgrupados(@RequestBody Map<String, Object> datos) {
        
        System.out.println("=== Datos recibidos ===");


        // Configura primero los datos del detalle
        
        // ... otros campos
        // Luego agrega el detalle a la venta
        


        List<Sale> ventas =  new ArrayList<>();
       
       

       
        

        
        
        // Imprimir todo el map como clave-valor
         datos.forEach((marca, productos) -> {
            System.out.println("\n--- Marca: " + marca + " ---");
            Sale sale = new Sale();
       
            
            // Convertir a lista para recorrer los objetos
            if (productos instanceof List) {
                List<Map<String, Object>> listaProductos = (List<Map<String, Object>>) productos;
                
                
                
                for (int i = 0; i < listaProductos.size(); i++) {
                    Map<String, Object> producto = listaProductos.get(i);
                    DetailsSale detailsSale = new DetailsSale();
                    
                    Product product = new Product();

                    String id = producto.get("id").toString();

                    String Cantidad = producto.get("cantidad").toString();
                    String Precio = producto.get("precio").toString();

                    product.setId(Integer.parseInt(id));
                    System.out.println("ID del producto: " + product.getId());
                    
                    detailsSale.setProducto(product);
                    detailsSale.setCantidad(Integer.parseInt(Cantidad));
                    detailsSale.setPrecio(Double.parseDouble(Precio));
                    detailsSale.setSubtotal(detailsSale.getCantidad() * detailsSale.getPrecio());
                    sale.addDetalle(detailsSale);
                    System.out.println("  Producto " + (i + 1) + ":");
                    System.out.println("    ID: " + producto.get("id"));
                    System.out.println("    Marca: " + producto.get("marca"));
                    System.out.println("    Cantidad: " + producto.get("cantidad"));
                    System.out.println("    Precio: " + producto.get("precio"));

                    
                }
                 ventas.add(sale);
                
            }
        });

         System.out.println("=== Ventas ===");  
        System.out.println(ventas);
        System.out.println(ventas.size());

        
        return ResponseEntity.ok().build();
    }




}
