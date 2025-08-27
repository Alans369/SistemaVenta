package com.SistemaVenta.demo.Repositorios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.SistemaVenta.demo.Model.Sale;

public interface ISaleRepository extends JpaRepository <Sale,Integer> {

     @Query("SELECT DISTINCT v FROM Sale v " +
           "JOIN v.detallesVenta d " +
           "JOIN d.producto p " +
           "JOIN p.marca m " +
           "WHERE m.id = :marcaId")
    Page<Sale> findVentasByMarcaProducto(@Param("marcaId") Integer marcaId, Pageable pageable);
    




}
