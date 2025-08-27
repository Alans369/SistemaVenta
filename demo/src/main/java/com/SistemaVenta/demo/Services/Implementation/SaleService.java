package com.SistemaVenta.demo.Services.Implementation;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.SistemaVenta.demo.Model.DetailsSale;
import com.SistemaVenta.demo.Model.Product;
import com.SistemaVenta.demo.Model.Sale;
import com.SistemaVenta.demo.Repositorios.IProductRepository;
import com.SistemaVenta.demo.Repositorios.ISaleRepository;
import com.SistemaVenta.demo.Services.Interfaces.ISale;

import jakarta.persistence.EntityNotFoundException;

@Service
public class SaleService implements ISale{
    @Autowired
    private ISaleRepository repository;

    @Autowired 
    private IProductRepository productRepository;

    @Override
    public void actualizarStockProducto(Integer productoId, Integer cantidadVendida) {
         Product producto = productRepository.findById(productoId)
            .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado: " + productoId));
        
        int stockActual = producto.getStock();
        int cantidadInt = cantidadVendida;
        
        if (stockActual < cantidadInt) {
            throw new IllegalArgumentException(
                "Stock insuficiente para el producto: " + producto.getNombre() + 
                ". Stock disponible: " + stockActual + 
                ", cantidad solicitada: " + cantidadInt
            );
        }
        
        int nuevoStock = stockActual - cantidadInt;
        producto.setStock(nuevoStock);
        productRepository.save(producto);
        
    }

    @Override
    public Sale save(Sale venta) {
         for (DetailsSale detalle : venta.getDetallesVenta()) {
            actualizarStockProducto(detalle.getProducto().getId(), detalle.getCantidad());
        }
        
        // Calcular el total de la venta
        double total = venta.getDetallesVenta().stream()
            .mapToDouble(detalle -> detalle.getPrecio() * detalle.getCantidad())
            .sum();
        venta.setTotal(total);
        
        // Establecer la fecha actual
        venta.setFecha(LocalDateTime.now());
        
        // Guardar la venta
        return repository.save(venta);
    }

    @Override
    public Page<Sale> obtenerTodos(Integer marca,Pageable pageable) {
        
        return repository.findVentasByMarcaProducto(marca,pageable);
    }

    @Override
    public Sale obtenerVentaPorId(Integer id) {
        return repository.findById(id).orElse(null);
    }

    



    

}
