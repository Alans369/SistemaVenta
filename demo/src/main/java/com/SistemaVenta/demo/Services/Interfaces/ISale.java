package com.SistemaVenta.demo.Services.Interfaces;

import com.SistemaVenta.demo.Model.Sale;

public interface ISale {

    Sale save(Sale venta);

    void actualizarStockProducto(Integer productoId, Integer cantidadVendida);
    

}
