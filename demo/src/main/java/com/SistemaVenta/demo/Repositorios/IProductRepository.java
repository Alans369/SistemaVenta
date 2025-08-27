package com.SistemaVenta.demo.Repositorios;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.SistemaVenta.demo.Model.Product;

public interface IProductRepository extends JpaRepository<Product, Integer> {
    @Modifying
    @Transactional
    @Query("UPDATE Product p SET p.estado = false WHERE p.id = :id")
    int softDeleteById(@Param("id") Integer id);

    @Query("SELECT p FROM Product p WHERE " +
           "(:nombre IS NULL OR p.nombre LIKE (CONCAT(:nombre, '%'))) AND " +
           "(:categoryId IS NULL OR p.category.id = :categoryId) AND" +
           "(p.marca.id = :marcaId) AND" +
           "(p.estado =:estado)" )
    Page<Product> findWithFilters(@Param("nombre") String nombre, 
                               @Param("categoryId") Integer categoryId,
                               @Param("marcaId") Integer marcaId,
                                @Param("estado") boolean  estado,
                               Pageable pageable);

     @Query("SELECT p FROM Product p WHERE p.estado = true")
     List<Product> findByEstadoTrue();

   
    

}
