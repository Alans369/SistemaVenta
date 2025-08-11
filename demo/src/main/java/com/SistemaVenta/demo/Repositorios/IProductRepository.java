package com.SistemaVenta.demo.Repositorios;
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

}
