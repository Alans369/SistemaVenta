package com.SistemaVenta.demo.Services.Interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.SistemaVenta.demo.Model.Sale;

public interface ISale {

    Sale save(Sale venta);

    void actualizarStockProducto(Integer productoId, Integer cantidadVendida);

    Sale obtenerVentaPorId(Integer id);

    Page<Sale> obtenerTodos(Integer marca, Pageable pageable);

    

}
