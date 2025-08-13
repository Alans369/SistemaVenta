package com.SistemaVenta.demo.Repositorios;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.SistemaVenta.demo.Model.Role;
import com.SistemaVenta.demo.Model.Sale;

public interface IRoleRepository extends JpaRepository<Role, Integer> {

    @Query("SELECT DISTINCT s FROM Sale s " +
           "JOIN s.detallesVenta d " +
           "JOIN d.producto p " +
           "WHERE p.marca.id = :marcaId " +
           "ORDER BY s.fecha DESC")
    Page<Sale> findVentasByMarcaId(@Param("marcaId") Integer marcaId, Pageable pageable);
    
    

}
