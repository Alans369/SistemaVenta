package com.SistemaVenta.demo.Repositorios;

import com.SistemaVenta.demo.Model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISaleRepository extends JpaRepository <Sale,Integer> {

     @Query("SELECT DISTINCT v FROM Sale v " +
           "JOIN v.detallesVenta d " +
           "JOIN d.producto p " +
           "WHERE p.marca = :marca")
    Page<Sale> findVentasByMarcaProducto(@Param("marca") String marca, Pageable pageable);

}
