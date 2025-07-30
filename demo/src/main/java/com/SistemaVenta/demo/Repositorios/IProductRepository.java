package com.SistemaVenta.demo.Repositorios;
import com.SistemaVenta.demo.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductRepository extends JpaRepository<Product, Integer> {
    // Aquí puedes definir métodos personalizados si es necesario
    // Por ejemplo, para buscar productos por nombre, categoría, etc.

}
